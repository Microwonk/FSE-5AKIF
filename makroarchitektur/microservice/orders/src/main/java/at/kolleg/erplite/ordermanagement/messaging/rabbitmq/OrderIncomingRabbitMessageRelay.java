package at.kolleg.erplite.ordermanagement.messaging.rabbitmq;

import at.kolleg.erplite.ordermanagement.ports.in.OrderIncomingMessagesPort;
import at.kolleg.erplite.sharedkernel.events.OrderDeliveredEvent;
import at.kolleg.erplite.sharedkernel.events.OrderInDeliveryEvent;
import at.kolleg.erplite.sharedkernel.events.OrderPackedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;


public class OrderIncomingRabbitMessageRelay {

    @Autowired
    private OrderIncomingMessagesPort orderIncomingMessagesPort;

    @RabbitListener(queues = "q.orderpacked2")
    public void receive(OrderPackedEvent orderPackedEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order packed event received for order# " + orderPackedEvent.orderId() + "!");
        this.orderIncomingMessagesPort.handle(orderPackedEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order packed event handled for order# " + orderPackedEvent.orderId() + "!");
    }

    @RabbitListener(queues = "q.order_delivered")
    public void receive(OrderDeliveredEvent orderDeliveredEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order delivered event received for order# " + orderDeliveredEvent.orderID() + "!");
        this.orderIncomingMessagesPort.handle(orderDeliveredEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order delivered event handled for order# " + orderDeliveredEvent.orderID() + "!");
    }

    @RabbitListener(queues = "q.order_in_delivery")
    public void receive(OrderInDeliveryEvent orderInDeliveryEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order in delivery  event received for order# " + orderInDeliveryEvent.orderID() + "!");
        this.orderIncomingMessagesPort.handle(orderInDeliveryEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order delivered event handled for order# " + orderInDeliveryEvent.orderID() + "!");
    }
}
