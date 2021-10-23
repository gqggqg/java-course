package guice.bind;

import com.google.inject.name.Names;
import guice.Application;
import guice.logger.CombinedLogger;
import guice.logger.ConsoleLogger;
import guice.logger.FileLogger;
import guice.logger.ILogger;

import com.google.inject.AbstractModule;

import org.jetbrains.annotations.NotNull;

public final class AppModule extends AbstractModule {

    private final String[] args;

    public AppModule(@NotNull String[] args) {
        this.args = args;
    }

    @Override
    protected void configure() {
        bind(ILogger.class).to(CombinedLogger.class);
        bind(ILogger.class).annotatedWith(FileLog.class).to(FileLogger.class);
        bind(ILogger.class).annotatedWith(ConsoleLog.class).to(ConsoleLogger.class);

        bind(String[].class).annotatedWith(Names.named("args")).toInstance(args);
        try {
            bind(Application.class).toConstructor(Application.class.getConstructor(String[].class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
