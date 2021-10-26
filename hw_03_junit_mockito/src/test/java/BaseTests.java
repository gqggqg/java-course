import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.Inject;

import net.lamberto.junit.GuiceJUnitRunner;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules(BaseTests.TestModule.class)
public class BaseTests {

    @Inject
    private LibraryFactory libraryFactory;

    @Inject
    private JsonBookFactory bookFactory;

    public static class TestModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(IBookFactory.class).to(JsonBookFactory.class);
            bind(String.class).annotatedWith(Names.named("filePath")).toInstance("books.txt");
        }
    }

    @Test(expected = IllegalStateException.class)
    public void bookCountIsGreaterLibraryCapacity() {
        libraryFactory.library(50);
    }

    @Test
    public void booksOrderAndEmptyCells() {
        int capacity = 110;
        ArrayList<Book> libraryBooks = libraryFactory.library(capacity).getBooks();

        ArrayList<Book> jsonBooks = (ArrayList<Book>)bookFactory.books();
        for (int i = jsonBooks.size(); i < capacity; i++) {
            jsonBooks.add(null);
        }

        System.out.println(libraryBooks.size());
        System.out.println(jsonBooks.size());

        Assert.assertArrayEquals(libraryBooks.toArray(), jsonBooks.toArray());
    }

    @Test(expected = IllegalStateException.class)
    public void takeBookFromEmptyCell() {
        int capacity = 110;
        libraryFactory.library(capacity).takeBook(capacity - 1);
    }

    @Test
    public void returnOfRequestedBook() {
        int capacity = 110;
        ILibrary library = libraryFactory.library(capacity);
        ArrayList<Book> libraryBooks = library.getBooks();

        int index = 10;
        Assert.assertEquals(libraryBooks.get(index), library.takeBook(index));
    }

    @Test
    public void bookReturnsToFirstEmptyCell() {
        int capacity = 110;
        ILibrary library = libraryFactory.library(capacity);

        int firstBookIndex = 7;
        int secondBookIndex = 16;
        library.takeBook(firstBookIndex);
        Book book = library.takeBook(secondBookIndex);
        library.addBook(book);

        ArrayList<Book> libraryBooks = library.getBooks();

        Assert.assertEquals(book, libraryBooks.get(firstBookIndex));
        Assert.assertEquals(secondBookIndex, libraryBooks.indexOf(null));
    }

    @Test(expected = IllegalStateException.class)
    public void addingBookToFilledLibrary() {
        int capacity = 100;
        libraryFactory.library(capacity).addBook(new Book(new Author("J.R."), "Harry Potter"));
    }
}
