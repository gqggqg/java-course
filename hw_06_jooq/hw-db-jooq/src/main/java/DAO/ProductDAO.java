package DAO;

import generated.tables.records.ProductRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.Collections;
import java.util.List;

import static generated.Tables.PRODUCT;

@RequiredArgsConstructor
public class ProductDAO implements DAO<ProductRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public ProductRecord get(long id) {
        ProductRecord record = null;

        try {
            record = context
                    .selectFrom(PRODUCT)
                    .where(PRODUCT.ID.eq(id))
                    .fetchOne();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        if (record == null) {
            return new ProductRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<ProductRecord> all() {
        List<ProductRecord> record = Collections.emptyList();

        try {
            return context
                    .selectFrom(PRODUCT)
                    .fetch();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return record;
    }

    @Override
    public boolean save(@NotNull ProductRecord record) {
        try {
            return context
                    .insertInto(PRODUCT)
                    .values(record.getId(),
                            record.getName(),
                            record.getCode())
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(@NotNull ProductRecord record) {
        try {
            return context
                    .update(PRODUCT)
                    .set(context.newRecord(PRODUCT, record))
                    .where(PRODUCT.ID.eq(record.getId()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(@NotNull ProductRecord record) {
        try {
            return context
                    .deleteFrom(PRODUCT)
                    .where(PRODUCT.ID.eq(record.getId()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }
}
