import Server.*;

import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.Objects;

@SuppressWarnings("NotNullNullableValidation")
public class Main {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(String[] args) throws Exception {
        FlywayInitializer.initDB();

        final Server server = new DefaultServer().build();

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.NO_SESSIONS
        );

        context.setContextPath("/");
        final URL resource = Main.class.getResource("/static");
        context.setBaseResource(Resource.newResource(Objects.requireNonNull(resource).toExternalForm()));
        context.setWelcomeFiles(new String[]{"welcome.html"});

        context.addServlet(
                new ServletHolder(
                        "default",
                        DefaultServlet.class
                ),
                "/*"
        );

        context.addServlet(
                new ServletHolder(
                        new DBServlet(CREDS.getUrl(), CREDS.getLogin(), CREDS.getPassword())
                ),
                "/db/*"
        );

        final String jdbcConfig = Objects.requireNonNull(Main.class.getResource("jdbc_config")).toExternalForm();
        final JDBCLoginService jdbcLoginService = new JDBCLoginService("login", jdbcConfig);
        final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(jdbcLoginService);

        server.addBean(jdbcLoginService);
        securityHandler.setHandler(context);
        server.setHandler(securityHandler);

        server.start();
        server.join();
    }
}
