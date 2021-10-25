import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.jetbrains.annotations.NotNull;

public class AppModule extends AbstractModule {

    private final String filePath;

    public AppModule(@NotNull String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected void configure() {
        bind(IBookFactory.class).to(JsonBookFactory.class);
        bind(String.class).annotatedWith(Names.named("filePath")).toInstance(filePath);
    }
}
