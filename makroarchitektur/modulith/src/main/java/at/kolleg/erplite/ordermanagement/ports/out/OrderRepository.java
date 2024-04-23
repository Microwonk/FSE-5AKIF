package at.kolleg.erplite.ordermanagement.ports.out;

import at.kolleg.erplite.ordermanagement.domain.Order;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderID;
import at.kolleg.erplite.sharedkernel.marker.OutputPortMarker;

import java.util.List;

@OutputPortMarker
public interface OrderRepository extends BaseRepository<Order, OrderID> {
    List<Order> getAllSortedAndPaged(int page, int pagesize, String sortedBy);

    void updateOrderWithNewState(Order order);
}
