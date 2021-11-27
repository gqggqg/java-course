import org.eclipse.jetty.server.Server;
import Server.*;

@SuppressWarnings("NotNullNullableValidation")
public class Main {

    public static void main(String[] args) throws Exception {
        FlywayInitializer.initDB();

        final Server server = new DefaultServer().build();
        server.start();
    }
}
