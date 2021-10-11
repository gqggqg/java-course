import java.io.InputStreamReader;
import com.google.gson.Gson;
import java.util.*;
import lombok.val;

public class JsonLibrary {

    private static final String path = "/books.json";

    private Map<Author, List<Book>> library;

    public JsonLibrary() {
        val stream = this.getClass().getResourceAsStream(path);
        if (stream == null) {
            return;
        }
        val reader = new InputStreamReader(stream);
        val books = new Gson().fromJson(reader, Book[].class);
        library = new HashMap<>();
        for (val book: books) {
            val author = book.getAuthor();
            if (!library.containsKey(author)) {
                library.put(author, new ArrayList<>());
            }
            library.get(author).add(book);
        }
    }

    public List<Book> GetBooksByAuthor(Author author) {
        if (library == null) {
            return Collections.emptyList();
        }
        return library.get(author);
    }
}
