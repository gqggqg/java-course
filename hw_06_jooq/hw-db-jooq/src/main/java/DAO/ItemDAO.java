package DAO;

import generated.tables.records.ItemRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.List;

import static generated.Tables.ITEM;

@RequiredArgsConstructor
public class ItemDAO implements DAO<ItemRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public ItemRecord get(long id) throws DataAccessException {
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
    public List<ItemRecord> all() throws DataAccessException {
        return context
                .selectFrom(ITEM)
                .fetch();
    }

    @Override
    public void save(@NotNull ItemRecord record) throws DataAccessException {
        context
                .insertInto(ITEM)
                .values(record.getId(),
                        record.getPrice(),
                        record.getProductId(),
                        record.getQuantity())
                .execute();
    }

    @Override
    public void update(@NotNull ItemRecord record) throws DataAccessException {
        context
                .update(ITEM)
                .set(context.newRecord(ITEM, record))
                .where(ITEM.ID.eq(record.getId()))
                .execute();
    }

    @Override
    public void delete(@NotNull ItemRecord record) throws DataAccessException {
        context
                .deleteFrom(ITEM)
                .where(ITEM.ID.eq(record.getId()))
                .execute();
    }
}
