package at.kolleg.erplite.ordermanagement.ports.out;

import at.kolleg.erplite.sharedkernel.marker.OutputPortMarker;

import java.util.List;
import java.util.Optional;

@OutputPortMarker
public interface BaseRepository<T, I> {
    Optional<T> insert(T domainObject);

    Optional<T> getById(I id);

    List<T> getAll();

    void deleteById(I id);
}
