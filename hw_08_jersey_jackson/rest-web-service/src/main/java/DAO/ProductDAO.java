package DAO;

import generated.db.tables.records.ProductRecord;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import org.jooq.DSLContext;

import static generated.db.Tables.PRODUCT;

@RequiredArgsConstructor
public final class ProductDAO implements DAO<ProductRecord> {

    final @NotNull DSLContext context;

    @NotNull
    @Override
    public ProductRecord get(int id) {
        var record = context
                .selectFrom(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .fetchOne();

        if (record == null) {
            return new ProductRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<ProductRecord> all() {
        return context
                .selectFrom(PRODUCT)
                .fetch();
    }

    @Override
    public void save(@NotNull ProductRecord record) {
        context
                .insertInto(PRODUCT)
                .set(PRODUCT.NAME, record.getName())
                .set(PRODUCT.MANUFACTURER, record.getManufacturer())
                .set(PRODUCT.QUANTITY, record.getQuantity())
                .execute();
    }

    @Override
    public void update(@NotNull ProductRecord record) {
        context
                .update(PRODUCT)
                .set(context.newRecord(PRODUCT, record))
                .where(PRODUCT.ID.eq(record.getId()))
                .execute();
    }

    @Override
    public void delete(int id) {
        context
                .deleteFrom(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .execute();
    }

    @NotNull
    public List<ProductRecord> getProductsByName(@NotNull String name) {
        return context
                .selectFrom(PRODUCT)
                .where(PRODUCT.NAME.eq(name))
                .fetch();
    }

    @NotNull
    public List<ProductRecord> getProductsByManufacturer(@NotNull String manufacturer) {
        return context
                .selectFrom(PRODUCT)
                .where(PRODUCT.MANUFACTURER.eq(manufacturer))
                .fetch();
    }

    public void delete(@NotNull String name) {
        context
                .deleteFrom(PRODUCT)
                .where(PRODUCT.NAME.eq(name))
                .execute();
    }
}
