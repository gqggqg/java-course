package DAO;

import json.Product;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class ProductDAO implements DAO<Product> {

    private final @NotNull Connection connection;

    private final @NotNull String SELECT_BY_ID = "SELECT * FROM product WHERE id=?";
    private final @NotNull String SELECT_BY_NAME = "SELECT * FROM product WHERE name=?";
    private final @NotNull String SELECT_BY_MANUFACTURER = "SELECT * FROM product WHERE manufacturer=?";
    private final @NotNull String SELECT_ALL = "SELECT * FROM product";
    private final @NotNull String INSERT = "INSERT INTO product(name,manufacturer,quantity) VALUES(?,?,?)";
    private final @NotNull String UPDATE = "UPDATE product SET name=?,manufacturer=?,quantity=? WHERE id=?";
    private final @NotNull String DELETE_BY_ID = "DELETE FROM product WHERE id=?";
    private final @NotNull String DELETE_BY_NAME = "DELETE FROM product WHERE name=?";

    @NotNull
    @Override
    public Product get(int id) throws IllegalStateException {
        try (var statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createProduct(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        throw new IllegalStateException("Product with id " + id + " not found");
    }

    @NotNull
    @Override
    public List<Product> all() {
        final List<Product> result = new ArrayList<>();

        try (var statement = connection.prepareStatement(SELECT_ALL)) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(createProduct(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void save(@NotNull Product entity) {
        try (var preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getManufacturer());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(@NotNull Product entity) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getManufacturer());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try(var preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Product with id = " + id + " not found");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @NotNull
    public List<Product> getProductsByName(@NotNull String name) {
        final List<Product> result = new ArrayList<>();

        try (var statement = connection.prepareStatement(SELECT_BY_NAME)) {
            statement.setString(1, name);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(createProduct(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @NotNull
    public List<Product> getProductsByManufacturer(@NotNull String manufacturer) {
        final List<Product> result = new ArrayList<>();

        try (var statement = connection.prepareStatement(SELECT_BY_MANUFACTURER)) {
            statement.setString(1, manufacturer);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(createProduct(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public void delete(@NotNull String name) {
        try(var preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {
            preparedStatement.setString(1, name);
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Product with name = " + name + " not found");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("manufacturer"),
                resultSet.getInt("quantity")
        );
    }
}
