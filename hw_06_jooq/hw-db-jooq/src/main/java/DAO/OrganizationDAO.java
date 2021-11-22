package DAO;

import generated.tables.records.OrganizationRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.List;

import static generated.Tables.ORGANIZATION;

@RequiredArgsConstructor
public class OrganizationDAO implements DAO<OrganizationRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public OrganizationRecord get(long id) throws DataAccessException {
        var record = context
                .selectFrom(ORGANIZATION)
                .where(ORGANIZATION.INN.eq(id))
                .fetchOne();

        if (record == null) {
            return new OrganizationRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<OrganizationRecord> all() throws DataAccessException {
        return context
                .selectFrom(ORGANIZATION)
                .fetch();
    }

    @Override
    public boolean save(@NotNull OrganizationRecord record) throws DataAccessException {
        return context
                .insertInto(ORGANIZATION)
                .values(record.getName(),
                        record.getInn(),
                        record.getAccount())
                .execute() == 1;
    }

    @Override
    public boolean update(@NotNull OrganizationRecord record) throws DataAccessException {
        return context
                .update(ORGANIZATION)
                .set(context.newRecord(ORGANIZATION, record))
                .where(ORGANIZATION.INN.eq(record.getInn()))
                .execute() == 1;
    }

    @Override
    public boolean delete(@NotNull OrganizationRecord record) throws DataAccessException {
        return context
                .deleteFrom(ORGANIZATION)
                .where(ORGANIZATION.INN.eq(record.getInn()))
                .execute() == 1;
    }
}
