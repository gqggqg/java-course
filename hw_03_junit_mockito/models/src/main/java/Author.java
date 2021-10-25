import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Author {

    @NonNull private String name;
}
