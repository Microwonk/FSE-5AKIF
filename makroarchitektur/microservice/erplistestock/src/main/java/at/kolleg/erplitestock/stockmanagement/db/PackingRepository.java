package at.kolleg.erplitestock.stockmanagement.db;


import at.kolleg.erplitestock.stockmanagement.business.Packing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(path = "packings") //SpringDataRest API http://localhost:8080/packings
public interface PackingRepository extends JpaRepository<Packing, Long> {
    Optional<Packing> findByOrderId(String orderId);
}
