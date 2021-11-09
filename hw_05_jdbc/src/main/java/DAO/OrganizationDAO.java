package DAO;

import entity.Organization;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO extends AbstractDAO<Organization> {

    public OrganizationDAO(@NotNull Connection connection) {
        super(connection, Queries.ORG_SELECT_BY_ID, Queries.ORG_SELECT_ALL, Queries.ORG_DELETE);
    }

    public void selectTopTenByProductQuantity() {
        System.out.println("The first 10 vendors by quantity of delivered products: ");
        for (var org : getEntitiesBySQL(Queries.ORG_SELECT_TOP_TEN_BY_PRODUCT_QUANTITY)) {
            printEntityInfo(org);
        }
    }

    public void selectByProductAndTotalSumPriceLimit(int productId, long limit) {
        System.out.println("Vendors with the sum of the delivered product above the limit: ");
        for (var org : getListByProductAndTotalSumPriceLimit(productId, limit)) {
            printEntityInfo(org);
        }
    }

    private List<Organization> getListByProductAndTotalSumPriceLimit(int productId, long limit) {
        try (var preparedStatement = connection.prepareStatement(Queries.ORG_SELECT_BY_PRODUCT_AND_TOTAL_SUM_PRICE_LIMIT)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setLong(2, limit);
            return getEntitiesByPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void save(@NotNull Organization entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.ORG_INSERT)) {
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, entity.getName());
            preparedStatement.setLong(parameterIndex++, entity.getINN());
            preparedStatement.setLong(parameterIndex, entity.getAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(@NotNull Organization entity) {
        try (var preparedStatement = connection.prepareStatement(Queries.ORG_UPDATE)) {
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, entity.getName());
            preparedStatement.setLong(parameterIndex++, entity.getAccount());
            preparedStatement.setLong(parameterIndex, entity.getINN());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected Organization createEntity(ResultSet resultSet) throws SQLException {
        return new Organization(
                resultSet.getString("name"),
                resultSet.getLong("INN"),
                resultSet.getLong("account")
        );
    }
}
