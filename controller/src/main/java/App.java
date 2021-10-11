import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import com.google.gson.Gson;
import java.util.Scanner;
import java.util.List;
import lombok.val;

public class App {

    public static void main(String[] args) {
        val author = GetAuthorFromConsole();
        if (author == null) {
            return;
        }
        val books = new LibraryFactory().GetBooksByAuthor(author);
        PrintSearchResults(books);
    }

    @Nullable
    private static Author GetAuthorFromConsole() {
        System.out.print("Enter the author's first and last name, separated by a space: ");
        Scanner scanner = new Scanner(System.in);
        val fullName = scanner.nextLine().split(" ");
        if (fullName.length != 2) {
            System.out.print("Input error. I have no idea. :)");
            return null;
        }
        return new Author(fullName[0], fullName[1]);
    }

    private static void PrintSearchResults(@NotNull List<Book> books) {
        if (books.isEmpty()) {
            System.out.print("Nothing was found in our wonderful library. :(");
            return;
        }
        System.out.format("Found %d books:\n", books.size());
        System.out.println(new Gson().toJson(books));
    }
}
