import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class JDBCCredentials {

    private static final @NotNull String PREFIX = "jdbc:postgresql";

    private final @NotNull String host;
    private final @NotNull String port;
    private final @NotNull String dataBaseName;
    private final @Getter @NotNull String login;
    private final @Getter @NotNull String password;

    public static final @NotNull JDBCCredentials DEFAULT = new JDBCCredentials(
            "127.0.0.1",
            "5432",
            "CoursesDB",
            "postgres",
            "postgres"
    );

    public @NotNull String getUrl() {
        return PREFIX
                + "://" + host + ':' + port
                + '/' + dataBaseName;
    }
}
