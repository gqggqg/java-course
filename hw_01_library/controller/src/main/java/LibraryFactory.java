import java.util.List;

public class LibraryFactory {

    public List<Book> GetBooksByAuthor(Author author) {
        return new JsonLibrary().GetBooksByAuthor(author);
    }
}
