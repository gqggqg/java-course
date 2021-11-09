package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class Waybill implements IEntity {

    private long number;
    private @NotNull Date date;
    private long organizationINN;

    @Override
    public long getId() {
        return number;
    }

    @Override
    public String toString() {
        return number + "\t" + date + '\t' + organizationINN;
    }
}
