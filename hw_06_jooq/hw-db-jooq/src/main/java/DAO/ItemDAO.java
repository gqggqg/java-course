package DAO;

import generated.tables.records.ItemRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import java.util.List;

import static generated.Tables.ITEM;

@RequiredArgsConstructor
public class ItemDAO implements DAO<ItemRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public ItemRecord get(long id) {
        var record = context
                .selectFrom(ITEM)
                .where(ITEM.ID.eq(id))
                .fetchOne();
        if (record == null) {
            return new ItemRecord();
        }
        return record;
    }

    @NotNull
    @Override
    public List<ItemRecord> all() {
        return context
                .selectFrom(ITEM)
                .fetch();
    }

    @Override
    public boolean save(@NotNull ItemRecord record) {
        return context
                .insertInto(ITEM)
                .values(record.getId(),
                        record.getPrice(),
                        record.getProductId(),
                        record.getQuantity())
                .execute() == 1;
    }

    @Override
    public boolean update(@NotNull ItemRecord record) {
        return context
                .update(ITEM)
                .set(context.newRecord(ITEM, record))
                .where(ITEM.ID.eq(record.getId()))
                .execute() == 1;
    }

    @Override
    public boolean delete(@NotNull ItemRecord record) {
        return context
                .deleteFrom(ITEM)
                .where(ITEM.ID.eq(record.getId()))
                .execute() == 1;
    }
}
