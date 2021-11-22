package DAO;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DAO<T> {

    @NotNull T get(long id);

    @NotNull List<T> all();

    void save(@NotNull T record);

    void update(@NotNull T record);

    void delete(@NotNull T record);
}
