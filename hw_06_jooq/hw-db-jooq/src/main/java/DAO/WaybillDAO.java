package DAO;

import generated.tables.records.WaybillRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.List;

import static generated.Tables.WAYBILL;

@RequiredArgsConstructor
public class WaybillDAO implements DAO<WaybillRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public WaybillRecord get(long id) throws DataAccessException {
        WaybillRecord record = context
                .selectFrom(WAYBILL)
                .where(WAYBILL.NUMBER.eq(id))
                .fetchOne();

        if (record == null) {
            return new WaybillRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<WaybillRecord> all() throws DataAccessException {
        return context
                .selectFrom(WAYBILL)
                .fetch();
    }

    @Override
    public boolean save(@NotNull WaybillRecord record) throws DataAccessException {
        return context
                .insertInto(WAYBILL)
                .values(record.getNumber(),
                        record.getDate(),
                        record.getOrganizationId())
                .execute() == 1;
    }

    @Override
    public boolean update(@NotNull WaybillRecord record) throws DataAccessException {
        return context
                .update(WAYBILL)
                .set(context.newRecord(WAYBILL, record))
                .where(WAYBILL.NUMBER.eq(record.getNumber()))
                .execute() == 1;
    }

    @Override
    public boolean delete(@NotNull WaybillRecord record) throws DataAccessException {
        return context
                .deleteFrom(WAYBILL)
                .where(WAYBILL.NUMBER.eq(record.getNumber()))
                .execute() == 1;
    }
}
