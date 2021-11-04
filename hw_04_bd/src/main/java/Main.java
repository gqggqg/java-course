import org.jetbrains.annotations.NotNull;
import org.flywaydb.core.Flyway;

public class Main {

    public static final @NotNull String CONNECTION = "jdbc:postgresql://localhost:5432/";
    public static final @NotNull String DB_NAME = "CoursesDB";
    public static final @NotNull String USERNAME = "postgres";
    public static final @NotNull String PASSWORD = "postgres";

    public static void main(String[] args) {
        final var flyway = Flyway
                .configure()
                .dataSource(CONNECTION + DB_NAME, USERNAME, PASSWORD)
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();

        System.out.println("Migrations applied successfully");
    }
}
