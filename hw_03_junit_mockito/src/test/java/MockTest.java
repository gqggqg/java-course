import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MockTest {

    @Mock
    @NotNull
    private JsonBookFactory bookFactory;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void printBooks() {
        int capacity = 5;

        ArrayList<Book> books = new ArrayList<>(Arrays.asList(
                new Book(new Author("author0"), "book0"),
                new Book(new Author("author1"), "book1")
        ));

        Mockito.when(bookFactory.books()).thenReturn(books);
        Library library = new Library(bookFactory, capacity);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(newOut);

        library.printAllBooksInConsole();
        StringBuilder stringBuilder = new StringBuilder();

        for (Book book: books) {
            stringBuilder.append(String.format("Book: %s, Author: %s\n", book.getName(), book.getAuthor().getName()));
        }

        String expected = stringBuilder.toString();
        String actual = baos.toString();

        System.out.flush();
        System.setOut(oldOut);

        Assert.assertEquals(expected, actual);
    }

}
