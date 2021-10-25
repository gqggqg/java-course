import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface IBookFactory {

    @NotNull
    Collection<Book> books();
}
