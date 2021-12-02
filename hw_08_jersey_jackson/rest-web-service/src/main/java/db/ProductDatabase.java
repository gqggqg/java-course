package db;

import DAO.ProductDAO;
import db.creds.JDBCCredentials;
import generated.db.tables.records.ProductRecord;
import json.Product;

import org.jetbrains.annotations.NotNull;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ProductDatabase {

    public enum Select {

        ALL,
        BY_NAME,
        BY_MANUFACTURER,
    }

    private static final JDBCCredentials CREDENTIALS = JDBCCredentials.DEFAULT;

    @NotNull
    public static List<Product> getProducts(@NotNull Select select, String filter) throws SQLException {
        return getProductRecords(select, filter)
                .stream()
                .map(p -> new Product(p.getId(), p.getName(), p.getManufacturer(), p.getQuantity()))
                .collect(Collectors.toList());
    }

    @NotNull
    private static List<ProductRecord> getProductRecords(@NotNull Select select, String filter) throws SQLException {
        try (var connection = DriverManager.getConnection(
                CREDENTIALS.getUrl(),
                CREDENTIALS.getLogin(),
                CREDENTIALS.getPassword())) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
            var productDAO = new ProductDAO(context);

            if (select != Select.ALL && filter == null) {
                return Collections.emptyList();
            }

            switch (select) {
                case ALL:
                    return productDAO.all();
                case BY_NAME:
                    return productDAO.getProductsByName(filter);
                case BY_MANUFACTURER:
                    return productDAO.getProductsByManufacturer(filter);
            }
        }

        return Collections.emptyList();
    }

    public static void addProduct(@NotNull Product product) throws SQLException {
        try (var connection = DriverManager.getConnection(
                CREDENTIALS.getUrl(),
                CREDENTIALS.getLogin(),
                CREDENTIALS.getPassword())) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
            var productDAO = new ProductDAO(context);
            productDAO.save(product);
        }
    }

    public static int deleteProductsByName(@NotNull String name) throws SQLException {
        try (var connection = DriverManager.getConnection(
                CREDENTIALS.getUrl(),
                CREDENTIALS.getLogin(),
                CREDENTIALS.getPassword())) {

            final DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);
            var productDAO = new ProductDAO(context);
            return productDAO.delete(name);
        }
    }
}
