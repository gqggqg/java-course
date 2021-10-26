import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.Inject;

import net.lamberto.junit.GuiceJUnitRunner;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
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
}
