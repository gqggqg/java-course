import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {

    @NonNull private Author author;
    @NonNull private String name;
}

