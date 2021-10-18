package guice;

import guice.bind.AppModule;

import com.google.inject.Guice;
import org.jetbrains.annotations.NotNull;

public class Main {

    public static void main(@NotNull String[] args) {
        final var injector = Guice.createInjector(new AppModule());
        injector.getInstance(Application.class).waitForInput();
    }
}
