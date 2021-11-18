package DAO;

import generated.tables.records.ItemRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.Collections;
import java.util.List;

import static generated.Tables.ITEM;

@RequiredArgsConstructor
public class ItemDAO implements DAO<ItemRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public ItemRecord get(long id) {
        ItemRecord record = null;

        try {
            record = context
                    .selectFrom(ITEM)
                    .where(ITEM.ID.eq(id))
                    .fetchOne();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        if (record == null) {
            return new ItemRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<ItemRecord> all() {
        List<ItemRecord> record = Collections.emptyList();

        try {
            return context
                    .selectFrom(ITEM)
                    .fetch();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return record;
    }

    @Override
    public boolean save(@NotNull ItemRecord record) {
        try {
            return context
                    .insertInto(ITEM)
                    .values(record.getId(),
                            record.getPrice(),
                            record.getProductId(),
                            record.getQuantity())
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(@NotNull ItemRecord record) {
        try {
            return context
                    .update(ITEM)
                    .set(context.newRecord(ITEM, record))
                    .where(ITEM.ID.eq(record.getId()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(@NotNull ItemRecord record) {
        try {
            return context
                    .deleteFrom(ITEM)
                    .where(ITEM.ID.eq(record.getId()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }
}
