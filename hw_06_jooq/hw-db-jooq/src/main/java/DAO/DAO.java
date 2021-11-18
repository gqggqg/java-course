package DAO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DAO<T> {

    @NotNull T get(long id);

    @NotNull List<T> all();

    boolean save(@NotNull T record);

    boolean update(@NotNull T record);

    boolean delete(@NotNull T record);
}
