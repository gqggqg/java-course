import db.init.FlywayInitializer;
import api.AuthenticationFilter;
import server.JettyServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

public final class JerseyLauncher extends Application {

    public static void main(String[] args) {
        FlywayInitializer.initDB();

        final Server server = JettyServer.build();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        final ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");

        final Map<String, String> param = new HashMap<>();
        param.put(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyLauncher.class.getName());
        param.put(ServerProperties.PROVIDER_PACKAGES, AuthenticationFilter.class.getPackage().getName());
        param.put(ServerProperties.PROVIDER_CLASSNAMES, JacksonFeature.class.getName());

        servletHolder.setInitParameters(param);
        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println("Failed to start server.");
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }
}
