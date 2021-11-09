package DAO;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public final class Queries {

    public static final @NotNull String ITEM_SELECT_BY_ID = "SELECT id,price,product_id,quantity FROM item WHERE id = ?";
    public static final @NotNull String ITEM_SELECT_ALL = "SELECT id,price,product_id,quantity FROM item";
    public static final @NotNull String ITEM_INSERT = "INSERT INTO item(id,price,product_id,quantity) VALUES(?,?,?,?)";
    public static final @NotNull String ITEM_UPDATE = "UPDATE item SET price = ?,product_id = ?,quantity = ? WHERE id = ?";
    public static final @NotNull String ITEM_DELETE = "DELETE FROM item WHERE id = ?";

    public static final @NotNull String ORG_SELECT_BY_ID = "SELECT name,INN,account FROM organization WHERE INN = ?";
    public static final @NotNull String ORG_SELECT_ALL = "SELECT name,INN,account FROM organization";
    public static final @NotNull String ORG_INSERT = "INSERT INTO organization(name,INN,account) VALUES(?,?,?)";
    public static final @NotNull String ORG_UPDATE = "UPDATE organization SET name = ?,account = ? WHERE INN = ?";
    public static final @NotNull String ORG_DELETE = "DELETE FROM organization WHERE INN = ?";

    public static final @NotNull String ORG_SELECT_TOP_TEN_BY_PRODUCT_QUANTITY = "SELECT O.name, O.INN, O.account, SUM(I.quantity) AS total from organization AS O\n" +
            "JOIN waybill AS W ON W.organization_INN = O.INN\n" +
            "JOIN waybill_item AS WI ON WI.waybill_id = W.number\n" +
            "JOIN item AS I ON I.id = WI.item_id\n" +
            "GROUP BY O.INN\n" +
            "ORDER BY total\n" +
            "DESC LIMIT 10";

    public static final @NotNull String ORG_SELECT_BY_PRODUCT_AND_TOTAL_SUM_PRICE_LIMIT = "SELECT O.name, O.INN, O.account from organization AS O\n" +
            "JOIN waybill AS W ON W.organization_INN = O.INN\n" +
            "JOIN waybill_item AS WI ON WI.waybill_id = W.number\n" +
            "JOIN item AS I ON I.id = WI.item_id\n" +
            "WHERE I.id = ?\n" +
            "GROUP BY O.INN\n" +
            "HAVING SUM(I.quantity * I.price) > ?\n";

    public static final @NotNull String PRODUCT_SELECT_BY_ID = "SELECT id,name,code FROM product WHERE id = ?";
    public static final @NotNull String PRODUCT_SELECT_ALL = "SELECT id,name,code FROM product";
    public static final @NotNull String PRODUCT_INSERT = "INSERT INTO product(id,name,code) VALUES(?,?,?)";
    public static final @NotNull String PRODUCT_UPDATE = "UPDATE product SET name = ?,code = ? WHERE id = ?";
    public static final @NotNull String PRODUCT_DELETE = "DELETE FROM product WHERE id = ?";

    public static final @NotNull String PRODUCT_SELECT_QUANTITY_AND_TOTAL_COST_FOR_PERIOD = "SELECT I.id, Pr.name, sum(I.quantity) AS total_quantity, sum(I.price) total_price from waybill AS W\n" +
            "JOIN waybill_item AS WI ON WI.waybill_id = W.number\n" +
            "JOIN item AS I ON I.id = WI.item_id\n" +
            "JOIN product AS Pr ON Pr.id = I.product_id\n" +
            "WHERE W.date >= ? AND W.date <= ?\n" +
            "GROUP BY I.id, Pr.name\n" +
            "ORDER BY total_quantity DESC";

    public static final @NotNull String PRODUCT_SELECT_AVG_PRICE_FOR_PERIOD = "SELECT AVG(I.price) AS avg_price from waybill AS W\n" +
            "JOIN waybill_item AS WI ON WI.waybill_id = W.number\n" +
            "JOIN item AS I ON I.id = WI.item_id\n" +
            "JOIN product AS Pr ON Pr.id = I.product_id\n" +
            "WHERE W.date >= ? AND W.date <= ?";

    public static final @NotNull String WAYBILL_SELECT_BY_ID = "SELECT number,data,organization_INN FROM waybill WHERE number = ?";
    public static final @NotNull String WAYBILL_SELECT_ALL = "SELECT number,data,organization_INN FROM waybill";
    public static final @NotNull String WAYBILL_INSERT = "INSERT INTO waybill(number,data,organization_INN) VALUES(?,?,?)";
    public static final @NotNull String WAYBILL_UPDATE = "UPDATE waybill SET data = ?,organization_INN = ? WHERE number = ?";
    public static final @NotNull String WAYBILL_DELETE = "DELETE FROM waybill WHERE number = ?";

    public static final @NotNull String WAYBILL_ITEM_SELECT_BY_ID = "SELECT id,waybill_id,item_id FROM waybill_item WHERE id = ?";
    public static final @NotNull String WAYBILL_ITEM_SELECT_ALL = "SELECT id,waybill_id,item_id FROM waybill_item";
    public static final @NotNull String WAYBILL_ITEM_INSERT = "INSERT INTO waybill_item(id,waybill_id,item_id) VALUES(?,?,?)";
    public static final @NotNull String WAYBILL_ITEM_UPDATE = "UPDATE waybill_item SET waybill_id = ?,item_id = ? WHERE id = ?";
    public static final @NotNull String WAYBILL_ITEM_DELETE = "DELETE FROM waybill_item WHERE id = ?";
}
