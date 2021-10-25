public interface ILibrary {

    Book takeBook(int cellNumber);

    void addBook(Book book);

    void printAllBooksInConsole();

    int size();
}
