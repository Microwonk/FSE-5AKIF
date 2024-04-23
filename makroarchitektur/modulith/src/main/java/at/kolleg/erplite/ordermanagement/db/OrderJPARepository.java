package at.kolleg.erplite.ordermanagement.db;

import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


interface OrderJPARepository extends JpaRepository<OrderDbEntity, String> {

    @Modifying //Variation for updating
    @Query("update versioned OrderDbEntity o set o.state = ?2 where o.orderID = ?1")
    void updateOrderState(String orderid, OrderState newState);
}