package data;

import clan.Config;
import io.vertx.core.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    public static @NotNull String getRandomName(String prefix) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final var postfixClassNumber = random.nextInt(Integer.MAX_VALUE);
        return prefix + postfixClassNumber;
    }

    public static @NotNull JsonObject getJsonObjectOfRoleMembersData(int number, int maxNumber) {
        var jsonObject = new JsonObject();
        jsonObject.put(Config.ROLE_NUMBER_KEY, number);
        jsonObject.put(Config.ROLE_MAX_NUMBER_KEY, maxNumber);
        return jsonObject;
    }

    public static @NotNull String getUserJoinEventAddress(String clanName) {
        return Config.USER_JOIN_EVENT + "_" + clanName;
    }
}
