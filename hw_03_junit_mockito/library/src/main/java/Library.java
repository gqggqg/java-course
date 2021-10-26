import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class Library implements ILibrary {

    @NotNull
    private final ArrayList<Book> books;

    public Library(@NotNull IBookFactory bookFactory, int capacity) throws IllegalStateException {
        Collection<Book> books = bookFactory.books();

        if (books.size() > capacity) {
            throw new IllegalStateException("The number of books is greater than the capacity of the library.");
        }

        this.books = new ArrayList<>(capacity);
        this.books.addAll(books);

        for (int i = books.size(); i < capacity; i++) {
            this.books.add(null);
        }
    }

    @Override
    public Book takeBook(int cellNumber) throws IllegalStateException {
        if (cellNumber > books.size()) {
            throw new IllegalStateException("Invalid cell number.");
        }

        if (books.get(cellNumber) == null) {
            throw new IllegalStateException("There is no book in the cell.");
        }

        Book book = books.get(cellNumber);
        books.set(cellNumber, null);

        logTakeBook(cellNumber, book);

        return book;
    }

    @Override
    public void addBook(Book book) throws IllegalStateException {
        if (!books.contains(null)) {
            throw new IllegalStateException("There are no empty cells.");
        }

        int index = books.indexOf(null);
        books.set(index, book);
    }

    private void logTakeBook(int cellNumber, @NotNull Book book) {
        System.out.printf("[TAKEN]\nCell: %d, Book: %s, Author: %s\n", cellNumber, book.getName(), book.getAuthor().getName());
    }

    @NotNull
    @Override
    public ArrayList<Book> getBooks() {
        return books;
    }

    @Override
    public void printAllBooksInConsole() {
        for (Book book : books) {
            if (book != null) {
                System.out.printf("Book: %s, Author: %s\n", book.getName(), book.getAuthor().getName());
            }
        }
    }

    @Override
    public int size() {
        return books.size();
    }
}
