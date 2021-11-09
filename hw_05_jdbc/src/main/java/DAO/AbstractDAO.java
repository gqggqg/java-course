package DAO;

import entity.IEntity;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractDAO<T extends IEntity> implements DAO<T> {

    protected final @NotNull Connection connection;

    private final @NotNull String SELECT_BY_ID;
    private final @NotNull String SELECT_ALL;
    private final @NotNull String DELETE;

    protected abstract T createEntity(ResultSet resultSet) throws SQLException;

    protected List<T> getEntitiesBySQL(@NotNull String sql) {
        return getEntitiesFromBD(sql, null);
    }

    protected List<T> getEntitiesByPreparedStatement(@NotNull PreparedStatement preparedStatement) {
        return getEntitiesFromBD(null, preparedStatement);
    }

    private List<T> getEntitiesFromBD(String sql, PreparedStatement preparedStatement) {
        final List<T> result = new ArrayList<>();
        try (var resultSet = sql == null ? preparedStatement.executeQuery() : connection.createStatement().executeQuery(sql)) {
            fillEntitiesListFromResultSet(result, resultSet);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void fillEntitiesListFromResultSet(List<T> list, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            list.add(createEntity(resultSet));
        }
    }

    protected void printEntityInfo(T entity) {
        System.out.println(entity);
    }

    @Override
    public @NotNull T get(long id) {
        try (var statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createEntity(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        throw new IllegalStateException("Record with id " + id + " not found");
    }

    @Override
    public @NotNull List<T> all() {
        return getEntitiesBySQL(SELECT_ALL);
    }

    @Override
    public void delete(@NotNull T entity) {
        try(var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
