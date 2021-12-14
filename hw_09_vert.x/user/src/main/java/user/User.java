package user;

import clan.Config;
import data.DataGenerator;
import io.vertx.core.*;
import io.vertx.core.eventbus.Message;
import io.vertx.core.shareddata.AsyncMap;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class User extends AbstractVerticle {

    private final String userName;

    private boolean inClan;
    private String clanName;

    private long timerOfPeriodicRequestsToJoinClan;

    public User() {
        this.userName = DataGenerator.getRandomName("user_");
        this.inClan = false;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        subscribe(startPromise);
    }

    private void subscribe(Promise<Void> promise) {
        subscribeToKickOutOfClanEvent()
                .compose(v -> subscribeToClanJoiningEvent())
                .onComplete(promise);
    }

    private Future<Void> subscribeToKickOutOfClanEvent() {
        Promise<Void> promise = Promise.promise();

        vertx.eventBus()
                .consumer(Config.USERS_KICK_EVENT, this::kickOutClanEventHandler)
                .completionHandler(promise);

        return promise.future();
    }

    private Future<Void> subscribeToClanJoiningEvent() {
        Promise<Void> promise = Promise.promise();

        vertx.eventBus().consumer(
                        userName,
                        this::joinClanEventHandler)
                .completionHandler(event -> {
                    if (event.succeeded()) {
                        startToSendOutRequestsToJoinClan();
                        promise.complete();
                    } else {
                        promise.fail(event.cause());
                    }
                });

        return promise.future();
    }

    private void joinClanEventHandler(@NotNull Message<String> event) {
        if (inClan) {
            return;
        }

        clanName = event.body();
        inClan = true;
        System.out.println("User " + userName + " has joined the " + clanName + " clan.");
        vertx.cancelTimer(timerOfPeriodicRequestsToJoinClan);
    }

    private void kickOutClanEventHandler(@NotNull Message<String> event) {
        var clan = event.body();
        if (!inClan || !Objects.equals(clanName, clan)) {
            return;
        }
        inClan = false;
        System.out.println("User " + userName + " has left the " + clanName + " clan.");
        startToSendOutRequestsToJoinClan();
    }

    private void startToSendOutRequestsToJoinClan() {
        timerOfPeriodicRequestsToJoinClan = vertx.setPeriodic(5000,
                timer -> sendRequestToJoinClan());
    }

    private void sendRequestToJoinClan() {
        vertx.sharedData().<String, Boolean>getAsyncMap(Config.CLAN_MAP_NAME, mapAsyncResult -> {
            if (mapAsyncResult.failed()) {
                return;
            }
            sendRequestToFirstActiveClan(mapAsyncResult.result());
        });
    }

    private void sendRequestToFirstActiveClan(@NotNull AsyncMap<String, Boolean> asyncMap) {
        AtomicBoolean isSent = new AtomicBoolean(false);

        asyncMap.entries(clans -> clans.result().forEach((clan, isActive) -> {
            if (isActive && !isSent.get()) {
                isSent.set(true);
                vertx.eventBus().send(DataGenerator.getUserJoinEventAddress(clan), userName);
            }
        }));
    }
}
