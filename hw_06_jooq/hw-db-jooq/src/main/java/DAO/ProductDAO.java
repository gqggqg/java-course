package DAO;

import generated.tables.records.ProductRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import java.util.List;

import static generated.Tables.PRODUCT;

@RequiredArgsConstructor
public class ProductDAO implements DAO<ProductRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public ProductRecord get(long id) {
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
    public boolean save(@NotNull ProductRecord record) {
        return context
                .insertInto(PRODUCT)
                .values(record.getId(),
                        record.getName(),
                        record.getCode())
                .execute() == 1;
    }

    @Override
    public boolean update(@NotNull ProductRecord record) {
        return context
                .update(PRODUCT)
                .set(context.newRecord(PRODUCT, record))
                .where(PRODUCT.ID.eq(record.getId()))
                .execute() == 1;
    }

    @Override
    public boolean delete(@NotNull ProductRecord record) {
        return context
                .deleteFrom(PRODUCT)
                .where(PRODUCT.ID.eq(record.getId()))
                .execute() == 1;
    }
}
