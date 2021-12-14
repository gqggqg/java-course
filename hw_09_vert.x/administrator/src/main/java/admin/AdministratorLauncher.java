package admin;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class AdministratorLauncher {

    public static void main(String[] args) {

        String clanName = null;

        if (args.length > 0) {
            clanName = args[0];
        }

        Administrator admin;

        if (clanName == null) {
            admin = new Administrator();
        } else {
            admin = new Administrator(clanName);
        }

        Vertx.clusteredVertx(
                new VertxOptions(),
                vertxResult -> {
                    final var options = new DeploymentOptions().setWorker(true);
                    vertxResult.result().deployVerticle(
                            admin,
                            options,
                            res -> {
                                if (res.succeeded()) {
                                    System.out.println("Administrator deployment succeeded! Id: " + res.result());
                                } else {
                                    var cause = res.cause();
                                    System.out.println("Deployment failed!");
                                    if (cause != null) {
                                        System.out.println(cause.getMessage());
                                    }
                                }
                            });
                }
        );
    }
}
