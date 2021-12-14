package admin;

import clan.ClanRoleData;

import io.vertx.core.*;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.AsyncMap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Administrator extends AbstractVerticle {

    private final static String USERS_KICK_EVENT = "clan.users.kick";
    private final static String MODERATORS_KICK_EVENT = "clan.moderators.kick";
    private final static String MODERATOR_JOIN_EVENT = "clan.moderators.join";
    private final static String INACTIVE_CLAN_APPEARED_EVENT = "clan.admin.inactive";

    private final static String CLAN_MAP_NAME = "clans";

    private final static String ROLE_NUMBER_KEY = "number";
    private final static String ROLE_MAX_NUMBER_KEY = "maxNumber";

    private final static int MAX_NUMBER_OF_MODERATORS = 10;
    private final static int MAX_NUMBER_OF_USER = 10;

    private final ClanRoleData moderators;
    private final ClanRoleData users;

    private String clanName;
    private Boolean clanIsNew;
    private Boolean inClan;

    private MessageConsumer<String> joinAnotherClanConsumer;

    public Administrator(String clanName) {
        this();
        this.clanName = clanName;
    }

    public Administrator() {
        this.moderators = new ClanRoleData("moderator");
        this.users = new ClanRoleData("user");
        this.inClan = false;
        this.clanIsNew = true;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        if (clanName == null) {
            clanName = getRandomClan();
        }

        vertx.sharedData().<String, Boolean>getAsyncMap(CLAN_MAP_NAME, clans -> {
            if (clans.succeeded()) {
                clans.result().get(clanName, isActiveAsyncResult -> {
                    if (isActiveAsyncResult.succeeded()) {

                        var activity = isActiveAsyncResult.result();
                        clanIsNew = activity == null;
                        var isActive = !clanIsNew && activity;

                        if (isActive) {
                            tryToJoinAnotherClan(startPromise);
                        } else {
                            activeClan(startPromise);
                        }
                    }
                });
            } else {
                startPromise.fail(clans.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        if (inClan) {
            inactiveClan(stopPromise);
        } else {
            stopPromise.complete();
        }
    }

    private void inactiveClan(Promise<Void> promise) {
        vertx.sharedData().<String, Boolean>getAsyncMap(CLAN_MAP_NAME, clans -> {
            if (clans.succeeded()) {
                clans.result().put(clanName, false, completion -> {
                    if (completion.succeeded()) {
                        sendClanInactivationEvent();
                        promise.complete();
                    } else {
                        promise.fail(completion.cause());
                    }
                });
            } else {
                promise.fail(clans.cause());
            }
        });
    }

    private void sendClanInactivationEvent() {
        vertx.eventBus().publish(INACTIVE_CLAN_APPEARED_EVENT, clanName);
    }

    private void tryToJoinAnotherClan(@NotNull Promise<Void> promise) {
        tryToJoinAnotherClan()
                .onSuccess(promise::complete)
                .onFailure(promise::fail);
    }

    private Future<Void> tryToJoinAnotherClan() {
        Promise<Void> promise = Promise.promise();

        tryToTakeInactiveClan()
                .onSuccess(v -> promise.complete())
                .onFailure(err -> {
                    joinAnotherClanConsumer = vertx.eventBus().consumer(INACTIVE_CLAN_APPEARED_EVENT);
                    joinAnotherClanConsumer.completionHandler(promise);
                    joinAnotherClanConsumer.handler(event -> {
                        clanName = event.body();
                        activeClan().onSuccess(v -> joinAnotherClanConsumer.unregister());
                    });
                });

        return promise.future();
    }

    private Future<Void> tryToTakeInactiveClan() {
        Promise<Void> promise = Promise.promise();

        vertx.sharedData().<String, Boolean>getAsyncMap(CLAN_MAP_NAME, mapAsyncResult -> {
            if (mapAsyncResult.succeeded()) {
                mapAsyncResult.result().entries(clans -> {
                    List<Future> futures = new ArrayList<>();
                    clans.result().forEach((clan, isActive) -> {
                        if (!isActive && !inClan) {
                            clanName = clan;
                            futures.add(activeClan());
                        }
                    });
                    CompositeFuture.any(futures)
                            .onSuccess(v -> promise.complete())
                            .onFailure(promise::fail);
                });
            } else {
                promise.fail(mapAsyncResult.cause());
            }
        });

        return promise.future();
    }

    private void activeClan(@NotNull Promise<Void> promise) {
        activeClan()
                .onSuccess(promise::complete)
                .onFailure(err -> tryToJoinAnotherClan(promise));
    }

    private Future<Void> activeClan() {
        Promise<Void> promise = Promise.promise();

        if (inClan) {
            promise.fail("Already in clan");
            return promise.future();
        }

        vertx.sharedData().<String, Boolean>getAsyncMap(CLAN_MAP_NAME, clans -> {
            if (clans.succeeded()) {
                clans.result().put(clanName, true, completion -> {
                    if (completion.succeeded()) {
                        startClan()
                        .onSuccess(v -> {
                            System.out.println("Clan " + clanName + " is active.");
                            inClan = true;
                            promise.complete();
                        })
                       .onFailure(err -> promise.fail(err.getCause()));
                    } else {
                        promise.fail(completion.cause());
                    }
                });
            } else {
                promise.fail(clans.cause());
            }
        });

        return promise.future();
    }

    private Future<Void> startClan() {
        Promise<Void> promise = Promise.promise();

        requestAndSetClanInformation()
                .compose(v -> CompositeFuture
                .all(setNewMaximumOfMembers(moderators), setNewMaximumOfMembers(users)))
                .compose(ar -> startAcceptingModerators())
                .onSuccess(v -> promise.complete())
                .onFailure(promise::fail);

        return promise.future();
    }

    private Future<Void> requestAndSetClanInformation() {
        Promise<Void> promise = Promise.promise();

        if (clanIsNew) {
            promise.complete();
            return promise.future();
        }

        CompositeFuture.all(
                        requestAndSetMembersInfo(moderators),
                        requestAndSetMembersInfo(users))
                .onSuccess(cf -> promise.complete())
                .onFailure(promise::fail);

        return promise.future();
    }

    private Future<Void> requestAndSetMembersInfo(@NotNull ClanRoleData members) {
        Promise<Void> promise = Promise.promise();

        vertx.sharedData().<String, JsonObject>getAsyncMap(members.role, mapAsyncResult -> {
            if (mapAsyncResult.succeeded()) {
                mapAsyncResult.result().get(clanName, jsonObjectAsyncResult -> {
                    if (jsonObjectAsyncResult.succeeded()) {
                        var jsonObject = jsonObjectAsyncResult.result();
                        members.number = jsonObject.getInteger(ROLE_NUMBER_KEY);
                        members.maxNumber = jsonObject.getInteger(ROLE_MAX_NUMBER_KEY);
                        promise.complete();
                    } else {
                        promise.fail(jsonObjectAsyncResult.cause());
                    }
                });
            } else {
                promise.fail(mapAsyncResult.cause());
            }
        });

        return promise.future();
    }

    private Future<Void> setNewMaximumOfMembers(ClanRoleData members) {
        Promise<Void> promise = Promise.promise();

        var needToSetNewMaximum = clanIsNew || ThreadLocalRandom.current().nextBoolean();

        if (!needToSetNewMaximum) {
            promise.complete();
            return promise.future();
        }

        vertx.sharedData().<String, JsonObject>getAsyncMap(members.role, mapAsyncResult -> {
            if (mapAsyncResult.succeeded()) {
                putNewRandomMaxToAsyncMap(mapAsyncResult.result(), members).onComplete(ar -> promise.complete());
            } else {
                promise.complete();
            }
        });

        return promise.future();
    }

    private Future<Void> putNewRandomMaxToAsyncMap(@NotNull AsyncMap<String, JsonObject> asyncMap, @NotNull ClanRoleData members) {
        Promise<Void> promise = Promise.promise();

        var newMaxNumber = getRandomNumberOfMembers(members.role);
        var jsonObject = getJsonObjectOfMembers(members.number, newMaxNumber);

        asyncMap.put(clanName, jsonObject, completion -> {
            if (completion.succeeded()) {
                members.maxNumber = newMaxNumber;
                System.out.printf(
                        "New max number of %s in the %s clan: %d\n",
                        members.role, clanName, members.maxNumber);
                if (members.isMaximum()) {
                    askMembersToLeaveClan(members);
                }
            }
            promise.complete();
        });

        return promise.future();
    }

    private void askMembersToLeaveClan(@NotNull ClanRoleData members) {
        var eventAddress = Objects.equals(members.role, moderators.role) ?
                MODERATORS_KICK_EVENT :
                USERS_KICK_EVENT;
        vertx.eventBus().publish(eventAddress, clanName);
    }

    private Future<Void> startAcceptingModerators() {
        Promise<Void> promise = Promise.promise();

        vertx.eventBus().consumer(
                MODERATOR_JOIN_EVENT,
                this::moderatorJoinEventHandler
        ).completionHandler(promise);

        return promise.future();
    }

    private void moderatorJoinEventHandler(Message<String> event) {
        if (!moderators.isMaximum()) {
            vertx.sharedData().<String, JsonObject>getAsyncMap(moderators.role, mapAsyncResult -> {
                if (mapAsyncResult.succeeded()) {
                    var asyncMap = mapAsyncResult.result();
                    var moderatorName = event.body();
                    tryAddModerator(asyncMap, moderatorName);
                }
            });
        }
    }

    private void tryAddModerator(@NotNull AsyncMap<String, JsonObject> asyncMap, String moderatorName) {
        var jsonObject = getJsonObjectOfMembers(moderators.number + 1, moderators.maxNumber);

        asyncMap.put(clanName, jsonObject, completion -> {
            if (completion.succeeded()) {
                moderators.number++;
                vertx.eventBus().send(moderatorName, clanName);
            }
        });
    }

    private @NotNull String getRandomClan() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final var postfixClassNumber = random.nextInt(Integer.MAX_VALUE);
        return "clan_" + postfixClassNumber;
    }

    private int getRandomNumberOfMembers(String role) {
        return ThreadLocalRandom.current().nextInt(Objects.equals(role, moderators.role) ?
                MAX_NUMBER_OF_MODERATORS :
                MAX_NUMBER_OF_USER);
    }

    private @NotNull JsonObject getJsonObjectOfMembers(int number, int maxNumber) {
        var jsonObject = new JsonObject();
        jsonObject.put(ROLE_NUMBER_KEY, number);
        jsonObject.put(ROLE_MAX_NUMBER_KEY, maxNumber);
        return jsonObject;
    }
}
