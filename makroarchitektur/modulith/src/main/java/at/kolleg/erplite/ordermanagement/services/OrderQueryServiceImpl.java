package at.kolleg.erplite.ordermanagement.services;

import at.kolleg.erplite.ordermanagement.domain.Order;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderID;
import at.kolleg.erplite.ordermanagement.ports.in.OrderQueryService;
import at.kolleg.erplite.ordermanagement.ports.out.OrderRepository;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderWithGivenIDNotFoundException;
import at.kolleg.erplite.ordermanagement.services.mapper.OrderResponseMapper;
import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;
import at.kolleg.erplite.sharedkernel.queries.GetAllOrdersSortedAndPagedQuery;
import at.kolleg.erplite.sharedkernel.queries.GetOrderByIdQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
class OrderQueryServiceImpl implements OrderQueryService {

    private OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getAllOrders() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling  getAllOrders query ...");
        List<OrderResponse> orderResponseList = new ArrayList<>();
        orderRepository.getAll().stream().map(order -> OrderResponseMapper.toResponseFromDomain(order)).forEach(orderResponseList::add);
        return orderResponseList;
    }

    @Override
    public List<OrderResponse> handle(GetAllOrdersSortedAndPagedQuery getAllOrdersSortedAndPagedQuery) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling  getAllOrdersSortedAndPaged query ...");
        List<OrderResponse> orderResponseList = new ArrayList<>();
        orderRepository.getAllSortedAndPaged(getAllOrdersSortedAndPagedQuery.page(), getAllOrdersSortedAndPagedQuery.pagesize(), getAllOrdersSortedAndPagedQuery.sortedBy()).stream().map(order -> OrderResponseMapper.toResponseFromDomain(order)).forEach(orderResponseList::add);
        return orderResponseList;
    }

    @Override
    public OrderResponse handle(GetOrderByIdQuery getOrderByIdQuery) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling  getOrderById query ...");
        if (getOrderByIdQuery.orderId() == null) {
            throw new OrderWithGivenIDNotFoundException("OrderQueryServiceImpl: Order with null-ID cannot be retrieved from DB!");
        } else {
            Optional<Order> optionalOrder = this.orderRepository.getById(new OrderID(getOrderByIdQuery.orderId()));
            if (optionalOrder.isPresent()) {
                return OrderResponseMapper.toResponseFromDomain(optionalOrder.get());
            } else {
                throw new OrderWithGivenIDNotFoundException("OrderQueryServiceImpl: Order with ID " + getOrderByIdQuery.orderId() + " was not present!");
            }
        }
    }
}
