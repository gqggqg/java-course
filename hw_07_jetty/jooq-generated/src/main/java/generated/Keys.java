/*
 * This file is generated by jOOQ.
 */
package generated;


import generated.tables.Product;
import generated.tables.records.ProductRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ProductRecord> PRODUCT_PK = Internal.createUniqueKey(Product.PRODUCT, DSL.name("product_pk"), new TableField[] { Product.PRODUCT.ID }, true);
}
