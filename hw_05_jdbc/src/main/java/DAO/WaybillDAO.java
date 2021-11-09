package DAO;

import entity.Waybill;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WaybillDAO extends AbstractDAO<Waybill> {

    public WaybillDAO(@NotNull Connection connection) {
        super(connection, Queries.WAYBILL_SELECT_BY_ID,  Queries.WAYBILL_SELECT_ALL, Queries.WAYBILL_DELETE);
    }

    @Override
    public void save(@NotNull Waybill entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.WAYBILL_INSERT)) {
            int parameterIndex = 1;
            preparedStatement.setLong(parameterIndex++, entity.getNumber());
            preparedStatement.setDate(parameterIndex++, entity.getDate());
            preparedStatement.setLong(parameterIndex, entity.getOrganizationINN());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Waybill entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.WAYBILL_UPDATE)) {
            int parameterIndex = 1;
            preparedStatement.setDate(parameterIndex++, entity.getDate());
            preparedStatement.setLong(parameterIndex++, entity.getOrganizationINN());
            preparedStatement.setLong(parameterIndex, entity.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected Waybill createEntity(ResultSet resultSet) throws SQLException {
        return new Waybill(
                resultSet.getLong("number"),
                resultSet.getDate("data"),
                resultSet.getLong("organization_INN")
        );
    }
}
