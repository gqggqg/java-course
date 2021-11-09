package DAO;

import entity.Product;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO(@NotNull Connection connection) {
        super(connection, Queries.PRODUCT_SELECT_BY_ID, Queries.PRODUCT_SELECT_ALL, Queries.PRODUCT_DELETE);
    }

    public void calculateProductQuantityAndCostFromPeriod(Date startDate, Date endDate) {
        System.out.println("Quantity and total cost of the product for the period: ");
        try (var preparedStatement = connection.prepareStatement(Queries.PRODUCT_SELECT_QUANTITY_AND_TOTAL_COST_FOR_PERIOD)) {
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var name = resultSet.getString("name");
                    var totalQuantity = resultSet.getLong("total_quantity");
                    var totalPrice = resultSet.getLong("total_price");
                    System.out.printf("%d\t%s\t%d\t%d\n", id, name, totalQuantity, totalPrice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calculateAVGPriceForPeriod(Date startDate, Date endDate) {
        System.out.print("Average price of received product for the period: ");
        try (var preparedStatement = connection.prepareStatement(Queries.PRODUCT_SELECT_AVG_PRICE_FOR_PERIOD)) {
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.printf("%f\n", resultSet.getDouble("avg_price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(@NotNull Product entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.PRODUCT_INSERT)) {
            int parameterIndex = 1;
            preparedStatement.setLong(parameterIndex++, entity.getId());
            preparedStatement.setString(parameterIndex++, entity.getName());
            preparedStatement.setLong(parameterIndex, entity.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Product entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.PRODUCT_UPDATE)) {
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, entity.getName());
            preparedStatement.setLong(parameterIndex++, entity.getCode());
            preparedStatement.setLong(parameterIndex, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected Product createEntity(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getLong("code")
        );
    }
}
