package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class Organization implements IEntity {

    private @NotNull String name;
    private long INN;
    private long account;

    @Override
    public long getId() {
        return INN;
    }

    @Override
    public String toString() {
        return name + '\t' + INN + '\t' + account;
    }
}
