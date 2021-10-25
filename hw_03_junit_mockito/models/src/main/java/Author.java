import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Author {

    @NonNull private String name;
}
