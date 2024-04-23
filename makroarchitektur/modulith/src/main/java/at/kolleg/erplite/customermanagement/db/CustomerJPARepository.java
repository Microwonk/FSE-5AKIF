package at.kolleg.erplite.customermanagement.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "customers") //SpringDataRest API auf http://localhost:8080/customers
interface CustomerJPARepository extends JpaRepository<CustomerDbEntity, String> {
}
