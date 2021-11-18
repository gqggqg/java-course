package DAO;

import generated.tables.records.WaybillRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.Collections;
import java.util.List;

import static generated.Tables.WAYBILL;

@RequiredArgsConstructor
public class WaybillDAO implements DAO<WaybillRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public WaybillRecord get(long id) {
        WaybillRecord record = null;

        try {
            record = context
                    .selectFrom(WAYBILL)
                    .where(WAYBILL.NUMBER.eq(id))
                    .fetchOne();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        if (record == null) {
            return new WaybillRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<WaybillRecord> all() {
        List<WaybillRecord> record = Collections.emptyList();

        try {
            return context
                    .selectFrom(WAYBILL)
                    .fetch();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return record;
    }

    @Override
    public boolean save(@NotNull WaybillRecord record) {
        try {
            return context
                    .insertInto(WAYBILL)
                    .values(record.getNumber(),
                            record.getDate(),
                            record.getOrganizationId())
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(@NotNull WaybillRecord record) {
        try {
            return context
                    .update(WAYBILL)
                    .set(context.newRecord(WAYBILL, record))
                    .where(WAYBILL.NUMBER.eq(record.getNumber()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(@NotNull WaybillRecord record) {
        try {
            return context
                    .deleteFrom(WAYBILL)
                    .where(WAYBILL.NUMBER.eq(record.getNumber()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }
}
