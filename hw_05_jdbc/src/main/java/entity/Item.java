package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item implements IEntity {

    private long id;
    private long price;
    private int productId;
    private long quantity;

    @Override
    public String toString() {
        return id + "\t" + price + '\t' + productId + '\t' + quantity;
    }
}
