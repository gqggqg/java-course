import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Book {

    @NonNull private Author author;
    @NonNull private String name;
}

