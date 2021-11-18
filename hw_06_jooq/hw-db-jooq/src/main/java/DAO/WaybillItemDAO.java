package DAO;

import generated.tables.records.WaybillItemRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.Collections;
import java.util.List;

import static generated.Tables.WAYBILL_ITEM;

@RequiredArgsConstructor
public class WaybillItemDAO implements DAO<WaybillItemRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public WaybillItemRecord get(long id) {
        WaybillItemRecord record = null;

        try {
            record = context
                    .selectFrom(WAYBILL_ITEM)
                    .where(WAYBILL_ITEM.ID.eq(id))
                    .fetchOne();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        if (record == null) {
            return new WaybillItemRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<WaybillItemRecord> all() {
        List<WaybillItemRecord> record = Collections.emptyList();

        try {
            return context
                    .selectFrom(WAYBILL_ITEM)
                    .fetch();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return record;
    }

    @Override
    public boolean save(@NotNull WaybillItemRecord record) {
        try {
            return context
                    .insertInto(WAYBILL_ITEM)
                    .values(record.getId(),
                            record.getWaybillId(),
                            record.getItemId())
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(@NotNull WaybillItemRecord record) {
        try {
            return context
                    .update(WAYBILL_ITEM)
                    .set(context.newRecord(WAYBILL_ITEM, record))
                    .where(WAYBILL_ITEM.ID.eq(record.getId()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(@NotNull WaybillItemRecord record) {
        try {
            return context
                    .deleteFrom(WAYBILL_ITEM)
                    .where(WAYBILL_ITEM.ID.eq(record.getId()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }
}
