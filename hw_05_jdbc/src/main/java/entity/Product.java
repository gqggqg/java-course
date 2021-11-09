package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class Product implements IEntity {

    private long id;
    private @NotNull String name;
    private long code;

    @Override
    public String toString() {
        return id + '\t' + name + '\t' + code;
    }
}
