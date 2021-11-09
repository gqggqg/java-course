package DAO;

import entity.WaybillItem;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WaybillItemDAO extends AbstractDAO<WaybillItem> {

    public WaybillItemDAO(@NotNull Connection connection) {
        super(connection, Queries.WAYBILL_ITEM_SELECT_BY_ID, Queries.WAYBILL_ITEM_SELECT_ALL, Queries.WAYBILL_ITEM_DELETE);
    }

    @Override
    public void save(@NotNull WaybillItem entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.WAYBILL_ITEM_INSERT)) {
            int parameterIndex = 1;
            preparedStatement.setLong(parameterIndex++, entity.getId());
            preparedStatement.setLong(parameterIndex++, entity.getWaybillId());
            preparedStatement.setInt(parameterIndex, entity.getItemId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull WaybillItem entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.WAYBILL_ITEM_UPDATE)) {
            int parameterIndex = 1;
            preparedStatement.setLong(parameterIndex++, entity.getWaybillId());
            preparedStatement.setInt(parameterIndex++, entity.getItemId());
            preparedStatement.setLong(parameterIndex, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected WaybillItem createEntity(ResultSet resultSet) throws SQLException {
        return new WaybillItem(
                resultSet.getInt("id"),
                resultSet.getLong("waybill_id"),
                resultSet.getInt("item_id")
        );
    }
}
