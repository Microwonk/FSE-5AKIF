package at.kolleg.erplite.ordermanagement.services;

import at.kolleg.erplite.ordermanagement.domain.Order;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderID;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderState;
import at.kolleg.erplite.ordermanagement.ports.in.OrderIncomingMessagesPort;
import at.kolleg.erplite.ordermanagement.ports.out.OrderOutgoingMessageRelay;
import at.kolleg.erplite.ordermanagement.ports.out.OrderRepository;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderPaymentCheckFailedException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderStateChangeNotPossibleException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderWithGivenIDNotFoundException;
import at.kolleg.erplite.sharedkernel.events.OrderDeliveredEvent;
import at.kolleg.erplite.sharedkernel.events.OrderInDeliveryEvent;
import at.kolleg.erplite.sharedkernel.events.OrderPackedEvent;
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
    private OrderOutgoingMessageRelay orderOutgoingMessageRelay;


    @Transactional
    public void handle(OrderPackedEvent orderPackedEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order packed event ...");
        Optional<Order> optionalOrderToCheck = this.orderRepository.getById(new OrderID(orderPackedEvent.orderId()));
        if (optionalOrderToCheck.isPresent()) {
            Order order = optionalOrderToCheck.get();

            if(order.getState() == OrderState.PREPARING_FOR_DELIVERY)
            {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order already in state 'preparing_for_delivery', nothing to do!");

                return; //order already in preparing for delivery state ... idempotency
            }

            try {
                order.orderStateTransitionTo(OrderState.PREPARING_FOR_DELIVERY);
                this.orderRepository.updateOrderWithNewState(order);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order state changed to preparing_for_delivery, changed order persisted!");

            } catch (OrderStateChangeNotPossibleException orderStateChangeNotPossibleException) {
                throw new OrderPaymentCheckFailedException("Order state change to 'preparing for delivery' not possible! " + orderStateChangeNotPossibleException.getMessage());
            }
        } else {
            throw new OrderWithGivenIDNotFoundException("Order with Id " + orderPackedEvent.orderId() + " not found for state change to preparing for delivery!");
        }

    }

    @Override
    public void handle(OrderInDeliveryEvent orderInDeliveryEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling 'order in delivery' event ...");
        Optional<Order> optionalOrderToCheck = this.orderRepository.getById(new OrderID(orderInDeliveryEvent.orderID()));
        if (optionalOrderToCheck.isPresent()) {
            Order order = optionalOrderToCheck.get();

            if(order.getState() == OrderState.IN_DELIVERY)
            {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order already in state 'in_delivery', nothing to do!");
                return; //order already delivered ... idempotency
            }

            try {
                order.orderStateTransitionTo(OrderState.IN_DELIVERY);
                this.orderRepository.updateOrderWithNewState(order);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order state changed to 'in_delivery', changed order persisted!");
            } catch (OrderStateChangeNotPossibleException orderStateChangeNotPossibleException) {
                throw new OrderPaymentCheckFailedException("Order state change to 'in_delivery' not possible! " + orderStateChangeNotPossibleException.getMessage());
            }
        } else {
            throw new OrderWithGivenIDNotFoundException("Order with Id " + orderInDeliveryEvent.orderID() + " not found for state change to 'in_delivery'!");
        }
    }

    @Override
    public void handle(OrderDeliveredEvent orderDeliveredEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order delivered event ...");
        Optional<Order> optionalOrderToCheck = this.orderRepository.getById(new OrderID(orderDeliveredEvent.orderID()));
        if (optionalOrderToCheck.isPresent()) {
            Order order = optionalOrderToCheck.get();

            if(order.getState() == OrderState.DELIVERED)
            {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order already in state 'delivered', nothing to do!");

                return; //order already delivered ... idempotency
            }

            try {
                order.orderStateTransitionTo(OrderState.DELIVERED);
                this.orderRepository.updateOrderWithNewState(order);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order state changed to delivered, changed order persisted!");
            } catch (OrderStateChangeNotPossibleException orderStateChangeNotPossibleException) {
                throw new OrderPaymentCheckFailedException("Order state change to 'delivered' not possible! " + orderStateChangeNotPossibleException.getMessage());
            }
        } else {
            throw new OrderWithGivenIDNotFoundException("Order with Id " + orderDeliveredEvent.orderID() + " not found for state change to preparing for delivery!");
        }
    }
}
