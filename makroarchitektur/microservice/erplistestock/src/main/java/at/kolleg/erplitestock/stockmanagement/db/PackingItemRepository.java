package at.kolleg.erplitestock.stockmanagement.db;

import at.kolleg.erplitestock.stockmanagement.business.PackingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "packingitems") //SpringDataRest Api  auf http://localhost:8080/packingitems
//https://www.baeldung.com/spring-data-rest-intro
public interface PackingItemRepository extends JpaRepository<PackingItem, Long> {

}
