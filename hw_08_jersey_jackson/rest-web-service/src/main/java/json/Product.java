package json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Product {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("quantity")
    private int quantity;

    @JsonCreator
    public Product(@JsonProperty("id") int id,
                   @JsonProperty(value = "name", required = true) String name,
                   @JsonProperty(value = "manufacturer", required = true) String manufacturer,
                   @JsonProperty(value = "quantity", required = true) int quantity) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product { " +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", manufacturer = '" + manufacturer + '\'' +
                ", quantity = " + quantity +
                " }";
    }
}
