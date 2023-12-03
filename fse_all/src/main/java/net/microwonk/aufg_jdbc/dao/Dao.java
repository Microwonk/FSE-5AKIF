package net.microwonk.aufg_jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, I> {

    Optional<T> get(I id);

    List<T> getAll();

    void insert(T t);

    void update(T t, String[] params);

    void delete(T t);
}
