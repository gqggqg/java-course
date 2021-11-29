/*
 * This file is generated by jOOQ.
 */
package generated;


import generated.db.Db;
import generated.security.Security;

import java.util.Arrays;
import java.util.List;

import org.jooq.Schema;
import org.jooq.impl.CatalogImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultCatalog extends CatalogImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_CATALOG</code>
     */
    public static final DefaultCatalog DEFAULT_CATALOG = new DefaultCatalog();

    /**
     * The schema <code>db</code>.
     */
    public final Db DB = Db.DB;

    /**
     * The schema <code>security</code>.
     */
    public final Security SECURITY = Security.SECURITY;

    /**
     * No further instances allowed
     */
    private DefaultCatalog() {
        super("");
    }

    @Override
    public final List<Schema> getSchemas() {
        return Arrays.asList(
            Db.DB,
            Security.SECURITY
        );
    }
}
