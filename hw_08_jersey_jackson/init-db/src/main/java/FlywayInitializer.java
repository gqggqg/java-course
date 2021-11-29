import org.flywaydb.core.Flyway;

import org.jetbrains.annotations.NotNull;

public final class FlywayInitializer {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void initDB() {
        final var flyway = Flyway.configure()
                .dataSource(
                        CREDS.getUrl(),
                        CREDS.getLogin(),
                        CREDS.getPassword()
                )
                .schemas("public","db","security")
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();
    }
}
