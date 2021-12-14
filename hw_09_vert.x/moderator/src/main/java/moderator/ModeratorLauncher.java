package moderator;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class ModeratorLauncher {

    public static void main(String[] args) {
        Vertx.clusteredVertx(
                new VertxOptions(),
                vertxResult -> {
                    final var options = new DeploymentOptions().setWorker(true);
                    vertxResult.result().deployVerticle(
                            new Moderator(),
                            options,
                            res -> {
                                if (res.succeeded()) {
                                    System.out.println("Moderator deployment succeeded! Id: " + res.result());
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