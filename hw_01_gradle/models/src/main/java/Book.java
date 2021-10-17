import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {

    @NonNull private String title;
    @NonNull private Author author;
    private int issueYear;
}
