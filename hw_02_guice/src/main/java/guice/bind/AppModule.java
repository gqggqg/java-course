package guice.bind;

import guice.logger.CombinedLogger;
import guice.logger.ConsoleLogger;
import guice.logger.FileLogger;
import guice.logger.ILogger;

import com.google.inject.AbstractModule;

public final class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ILogger.class).to(CombinedLogger.class);
        bind(ILogger.class).annotatedWith(FileLog.class).to(FileLogger.class);
        bind(ILogger.class).annotatedWith(ConsoleLog.class).to(ConsoleLogger.class);
    }
}
