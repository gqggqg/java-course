import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;

public class Application {

    public static void main(@NotNull String[] args) {
        String filePath;
        int capacity;

        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Allowed number of arguments is two.");
            }
            filePath = args[0];
            capacity = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid command line argument");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        final Injector injector = Guice.createInjector(new AppModule(filePath));

        try {
            ILibrary library = injector.getInstance(LibraryFactory.class).library(capacity);
            Book b = library.takeBook(1);
            library.addBook(b);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
