package at.kolleg.erplite.ordermanagement.services;

import at.kolleg.erplite.ordermanagement.domain.Order;
import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPackedEvent;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderState;
import at.kolleg.erplite.ordermanagement.ports.in.OrderIncomingMessagesPort;
import at.kolleg.erplite.ordermanagement.ports.out.OrderRepository;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderPaymentCheckFailedException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderStateChangeNotPossibleException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderWithGivenIDNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class OrderIncomingMessagesPortImpl implements OrderIncomingMessagesPort {
    private OrderRepository orderRepository;


    @Transactional
    public void handle(OrderPackedEvent orderPackedEvent) {
        //Meterialize object into Memory, place changes, and forward the domain object to repository
        //this ensures, that businesslogic will be executed und object is in consistent state.
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order packed event ...");
        Optional<Order> optionalOrderToCheck = this.orderRepository.getById(orderPackedEvent.orderId());
        if (optionalOrderToCheck.isPresent()) {
            Order order = optionalOrderToCheck.get();
            try {
                order.orderStateTransitionTo(OrderState.PREPARING_FOR_DELIVERY);
                this.orderRepository.updateOrderWithNewState(order);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order state changed to preparing_for_delivery, changed order persisted!");
            } catch (OrderStateChangeNotPossibleException orderStateChangeNotPossibleException) {
                throw new OrderPaymentCheckFailedException("Order state change to preparing for delivery not possible! " + orderStateChangeNotPossibleException.getMessage());
            }
        } else {
            throw new OrderWithGivenIDNotFoundException("Order with Id " + orderPackedEvent.orderId().id() + " not found for state change to preparing for delivery!");
        }

    }
}
