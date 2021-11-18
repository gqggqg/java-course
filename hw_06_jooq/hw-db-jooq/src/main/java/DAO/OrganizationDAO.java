package DAO;

import generated.tables.records.OrganizationRecord;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.exception.DataAccessException;
import org.jooq.DSLContext;
import java.util.Collections;
import java.util.List;

import static generated.Tables.ORGANIZATION;

@RequiredArgsConstructor
public class OrganizationDAO implements DAO<OrganizationRecord> {

    final DSLContext context;

    @NotNull
    @Override
    public OrganizationRecord get(long id) {
        OrganizationRecord record = null;

        try {
            record = context
                    .selectFrom(ORGANIZATION)
                    .where(ORGANIZATION.INN.eq(id))
                    .fetchOne();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        if (record == null) {
            return new OrganizationRecord();
        }

        return record;
    }

    @NotNull
    @Override
    public List<OrganizationRecord> all() {
        List<OrganizationRecord> record = Collections.emptyList();

        try {
            return context
                    .selectFrom(ORGANIZATION)
                    .fetch();
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return record;
    }

    @Override
    public boolean save(@NotNull OrganizationRecord record) {
        try {
            return context
                    .insertInto(ORGANIZATION)
                    .values(record.getName(),
                            record.getInn(),
                            record.getAccount())
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(@NotNull OrganizationRecord record) {
        try {
            return context
                    .update(ORGANIZATION)
                    .set(context.newRecord(ORGANIZATION, record))
                    .where(ORGANIZATION.INN.eq(record.getInn()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(@NotNull OrganizationRecord record) {
        try {
            return context
                    .deleteFrom(ORGANIZATION)
                    .where(ORGANIZATION.INN.eq(record.getInn()))
                    .execute() == 1;
        } catch (DataAccessException e) {
            System.err.println("Something went wrong executing the query.");
            e.printStackTrace();
        }

        return false;
    }
}
