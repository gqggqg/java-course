package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WaybillItem implements IEntity {

    private long id;
    private long waybillId;
    private int itemId;

    @Override
    public String toString() {
        return id + "\t" + waybillId + '\t' + itemId;
    }
}
