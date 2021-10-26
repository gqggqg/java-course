import java.util.ArrayList;

public interface ILibrary {

    Book takeBook(int cellNumber);

    void addBook(Book book);

    ArrayList<Book> getBooks();

    void printAllBooksInConsole();

    int size();
}
