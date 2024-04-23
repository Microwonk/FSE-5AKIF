package at.kolleg.erplite.ordermanagement.ports.in;

import at.kolleg.erplite.sharedkernel.queries.GetAllOrdersSortedAndPagedQuery;
import at.kolleg.erplite.sharedkernel.queries.GetOrderByIdQuery;
import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;

import java.util.List;

public interface OrderQueryService {
    List<OrderResponse> getAllOrders();

    List<OrderResponse> handle(GetAllOrdersSortedAndPagedQuery getAllOrdersSortedAndPagedQuery);

    OrderResponse handle(GetOrderByIdQuery getOrderByIdQuery);
}
