package moderator;

import clan.ClanRoleData;
import clan.Config;
import data.DataGenerator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.AsyncMap;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Moderator extends AbstractVerticle {

    private final String moderatorName;
    private final ClanRoleData users;

    private boolean inClan;
    private String clanName;

    private long timerOfPeriodicRequestsToJoinClan;

    public Moderator() {
        this.moderatorName = DataGenerator.getRandomName("moderator_");
        this.inClan = false;
        users = new ClanRoleData(Config.ROLE_USER);
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
                .consumer(Config.MODERATORS_KICK_EVENT, this::kickOutClanEventHandler)
                .completionHandler(promise);

        return promise.future();
    }

    private Future<Void> subscribeToClanJoiningEvent() {
        Promise<Void> promise = Promise.promise();

        vertx.eventBus().consumer(
                moderatorName,
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
        System.out.println("Moderator " + moderatorName + " has joined the " + clanName + " clan.");

        vertx.cancelTimer(timerOfPeriodicRequestsToJoinClan);
        getInfoAboutClanUsers()
                .onSuccess(v -> subscribeToAcceptUserIntoClan());
    }

    private void kickOutClanEventHandler(@NotNull Message<String> event) {
        var clan = event.body();
        if (!inClan || !Objects.equals(clanName, clan)) {
            return;
        }
        inClan = false;
        System.out.println("Moderator " + moderatorName + " has left the " + clanName + " clan.");
        startToSendOutRequestsToJoinClan();
    }

    private void startToSendOutRequestsToJoinClan() {
        timerOfPeriodicRequestsToJoinClan = vertx.setPeriodic(3000,
                timer -> vertx.eventBus().send(Config.MODERATOR_JOIN_EVENT, moderatorName));
    }

    private Future<Void> getInfoAboutClanUsers() {
        Promise<Void> promise = Promise.promise();

        vertx.sharedData().<String, JsonObject>getAsyncMap(Config.ROLE_USER, mapAsyncResult -> {
            if (mapAsyncResult.succeeded()) {
                getUsersData(mapAsyncResult.result())
                        .onSuccess(v -> promise.complete())
                        .onFailure(promise::fail);
            } else {
                promise.fail(mapAsyncResult.cause());
            }
        });

        return promise.future();
    }

    private Future<Void> getUsersData(@NotNull AsyncMap<String, JsonObject> asyncMap) {
        Promise<Void> promise = Promise.promise();

        asyncMap.get(clanName, completion -> {
            if (completion.succeeded()) {
                var result = completion.result();
                users.number = result.getInteger(Config.ROLE_NUMBER_KEY);
                users.maxNumber = result.getInteger(Config.ROLE_MAX_NUMBER_KEY);
                promise.complete();
            } else {
                promise.fail(completion.cause());
            }
        });

        return promise.future();
    }

    private void subscribeToAcceptUserIntoClan() {
        vertx.eventBus().consumer(
                Config.USER_JOIN_EVENT + "_" + clanName,
                this::tryToAddUserToClan);
    }

    private void tryToAddUserToClan(Message<String> event) {
        if (!users.isMaximum()) {
            vertx.sharedData().<String, JsonObject>getAsyncMap(Config.ROLE_USER, mapAsyncResult -> {
                if (mapAsyncResult.succeeded()) {
                    var asyncMap = mapAsyncResult.result();
                    var userName = event.body();
                    tryAddUser(asyncMap, userName);
                }
            });
        }
    }

    private void tryAddUser(@NotNull AsyncMap<String, JsonObject> asyncMap, String userName) {
        var jsonObject = DataGenerator.getJsonObjectOfRoleMembersData(users.number + 1, users.maxNumber);

        asyncMap.put(clanName, jsonObject, completion -> {
            if (completion.succeeded()) {
                users.number++;
                vertx.eventBus().send(userName, clanName);
                System.out.println("Moderator " + moderatorName + " added user " + userName + " to the " + clanName + " clan.");
            }
        });
    }
}
