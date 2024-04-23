package at.itkolleg.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery,Long> {
    Optional<OrderDelivery> findByOrderID(String orderID);
}
