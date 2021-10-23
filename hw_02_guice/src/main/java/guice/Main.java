package guice;

import com.google.inject.Injector;
import guice.bind.AppModule;

import com.google.inject.Guice;
import org.jetbrains.annotations.NotNull;

public class Main {

    public static void main(@NotNull String[] args) {
        final Injector injector = Guice.createInjector(new AppModule(args));
        injector.getInstance(Application.class).waitForInput();
    }
}
