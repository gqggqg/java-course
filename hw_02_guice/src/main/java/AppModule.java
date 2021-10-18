import com.google.inject.AbstractModule;

public final class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ILogger.class).to(ConsoleLogger.class);
        bind(ILogger.class).annotatedWith(File.class).to(FileLogger.class);
    }
}
