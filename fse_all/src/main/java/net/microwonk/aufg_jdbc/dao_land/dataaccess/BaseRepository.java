package net.microwonk.aufg_jdbc.dao_land.dataaccess;

import java.util.Optional;
import java.util.List;

public interface BaseRepository<T, I> {
    Optional<T> insert(T entity);

    Optional<T> getById(I id);

    List<T> getAll();

    Optional<T> update(T entity);

    boolean deleteById(I id);

}
