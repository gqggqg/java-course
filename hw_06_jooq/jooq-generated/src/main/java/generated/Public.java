/*
 * This file is generated by jOOQ.
 */
package generated;


import generated.tables.Item;
import generated.tables.Organization;
import generated.tables.Product;
import generated.tables.Waybill;
import generated.tables.WaybillItem;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.item</code>.
     */
    public final Item ITEM = Item.ITEM;

    /**
     * The table <code>public.organization</code>.
     */
    public final Organization ORGANIZATION = Organization.ORGANIZATION;

    /**
     * The table <code>public.product</code>.
     */
    public final Product PRODUCT = Product.PRODUCT;

    /**
     * The table <code>public.waybill</code>.
     */
    public final Waybill WAYBILL = Waybill.WAYBILL;

    /**
     * The table <code>public.waybill_item</code>.
     */
    public final WaybillItem WAYBILL_ITEM = WaybillItem.WAYBILL_ITEM;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Item.ITEM,
            Organization.ORGANIZATION,
            Product.PRODUCT,
            Waybill.WAYBILL,
            WaybillItem.WAYBILL_ITEM
        );
    }
}
