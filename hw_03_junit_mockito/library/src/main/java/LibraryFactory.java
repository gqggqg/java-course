import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;

public class LibraryFactory {

    @NotNull
    private final IBookFactory bookFactory;

    @Inject
    public LibraryFactory(@NotNull IBookFactory bookFactory) {
        this.bookFactory = bookFactory;
    }

    public ILibrary library(int capacity) {
        return new Library(bookFactory, capacity);
    }
}
