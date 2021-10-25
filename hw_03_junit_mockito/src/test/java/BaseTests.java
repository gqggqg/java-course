import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.Inject;

import net.lamberto.junit.GuiceJUnitRunner;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

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
    public void bookCountIsGreaterLibraryCapacity() throws IllegalStateException {
        libraryFactory.library(50);
    }

    @Test
    public void booksOrderAndEmptyCells() {
        ILibrary library = libraryFactory.library(110);
        int librarySize = library.size();
        Book[] libraryBooks = new Book[librarySize];
        for (int i = 0; i < librarySize; i++) {
            libraryBooks[i] = library.takeBook(i);
        }

        Collection<Book> jsonBooks = bookFactory.books();
        int diffSize = librarySize - jsonBooks.size();
        for (int i = 0; i < diffSize; i++) {
            jsonBooks.add(null);
        }

        Assert.assertArrayEquals(jsonBooks.toArray(), libraryBooks);
    }
}
