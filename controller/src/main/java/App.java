import com.google.gson.Gson;
import lombok.val;

public class App {

    public static void main(String[] args) {
        val author = new Author("William", "Shakespeare");
        val books = new LibraryFactory().GetBooksByAuthor(author);
        System.out.println(new Gson().toJson(books));
    }
}
