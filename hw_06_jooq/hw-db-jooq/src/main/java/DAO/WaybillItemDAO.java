package DAO;

import generated.tables.records.WaybillItemRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.List;

import static generated.Tables.WAYBILL_ITEM;

@RequiredArgsConstructor
public class WaybillItemDAO implements DAO<WaybillItemRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public WaybillItemRecord get(long id) throws DataAccessException {
        WaybillItemRecord record = context
                .selectFrom(WAYBILL_ITEM)
                .where(WAYBILL_ITEM.ID.eq(id))
                .fetchOne();

        if (record == null) {
            return new WaybillItemRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<WaybillItemRecord> all() throws DataAccessException {
        return context
                .selectFrom(WAYBILL_ITEM)
                .fetch();
    }

    @Override
    public boolean save(@NotNull WaybillItemRecord record) throws DataAccessException {
        return context
                .insertInto(WAYBILL_ITEM)
                .values(record.getId(),
                        record.getWaybillId(),
                        record.getItemId())
                .execute() == 1;
    }

    @Override
    public boolean update(@NotNull WaybillItemRecord record) throws DataAccessException {
        return context
                .update(WAYBILL_ITEM)
                .set(context.newRecord(WAYBILL_ITEM, record))
                .where(WAYBILL_ITEM.ID.eq(record.getId()))
                .execute() == 1;
    }

    @Override
    public boolean delete(@NotNull WaybillItemRecord record) throws DataAccessException {
        return context
                .deleteFrom(WAYBILL_ITEM)
                .where(WAYBILL_ITEM.ID.eq(record.getId()))
                .execute() == 1;
    }
}
