package DAO;

import entity.Item;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAO extends AbstractDAO<Item> {

    public ItemDAO(@NotNull Connection connection) {
        super(connection, Queries.ITEM_SELECT_BY_ID, Queries.ITEM_SELECT_ALL, Queries.ITEM_DELETE);
    }

    @Override
    public void save(@NotNull Item entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.ITEM_INSERT)) {
            int parameterIndex = 1;
            preparedStatement.setLong(parameterIndex++, entity.getId());
            preparedStatement.setLong(parameterIndex++, entity.getPrice());
            preparedStatement.setInt(parameterIndex++, entity.getProductId());
            preparedStatement.setLong(parameterIndex, entity.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Item entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.ITEM_UPDATE)) {
            int parameterIndex = 1;
            preparedStatement.setLong(parameterIndex++, entity.getPrice());
            preparedStatement.setInt(parameterIndex++, entity.getProductId());
            preparedStatement.setLong(parameterIndex++, entity.getQuantity());
            preparedStatement.setLong(parameterIndex, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected Item createEntity(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getInt("id"),
                resultSet.getLong("price"),
                resultSet.getInt("product_id"),
                resultSet.getLong("quantity")
        );
    }

}
