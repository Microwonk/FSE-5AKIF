package net.microwonk.aufg_jdbc.dao_land.dataaccess;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T,I> {
    Optional<T> insert(T entity);
    Optional<T> getByID(I ID);
    List<T> getAll();
    Optional<T> update(T entity);
    void deleteByIT(I id);
}
