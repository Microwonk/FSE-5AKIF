package at.itkolleg.delivery;

import at.itkolleg.delivery.sharedkernel.OrderDeliveredEvent;
import at.itkolleg.delivery.sharedkernel.OrderInDeliveryEvent;
import at.itkolleg.delivery.sharedkernel.OrderInitiateDeliveryEvent;
import at.itkolleg.delivery.sharedkernel.OrderPackedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryIncomingMessageRelay {

    @Autowired
    private OrderDeliveryRepository orderDeliveryRepository;

    @Autowired
    private RabbitTemplate template;

    @RabbitListener(queues = "q.orderpacked1") //Fanout-Demo (
    public void receive(OrderPackedEvent orderPackedEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order packed event received in delivery MS for order# " + orderPackedEvent.orderId() + "!");
    }

    @RabbitListener(queues = "q.initiate_delivery")
    public void receive(OrderInitiateDeliveryEvent orderInitiateDeliveryEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Initiate Delivery event received in delivery MS for order# " + orderInitiateDeliveryEvent.orderID() + "!");

        if(orderDeliveryRepository.findByOrderID(orderInitiateDeliveryEvent.orderID()).isPresent()) //if delivery for order already initiated -> do nothing, duplicate request, idempotency!
        {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Initiate Delivery event for order# " + orderInitiateDeliveryEvent.orderID() + " rejected, order delivery already initated!");
            return;
        }

        OrderDelivery orderDelivery = new OrderDelivery(
                null,
                orderInitiateDeliveryEvent.orderID(),
                orderInitiateDeliveryEvent.customerID(),
                orderInitiateDeliveryEvent.customerFirstname(),
                orderInitiateDeliveryEvent.customerLastname(),
                orderInitiateDeliveryEvent.customerEmail(),
                orderInitiateDeliveryEvent.customerStreet(),
                orderInitiateDeliveryEvent.customerZipcode(),
                orderInitiateDeliveryEvent.customerCity(),
                orderInitiateDeliveryEvent.customerCountry(),
                false);

        orderDeliveryRepository.save(orderDelivery);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Delivery for order# " + orderInitiateDeliveryEvent.orderID() + " initatied and saved in delivery DB!");

        //Immediately publish event that order is in delivery
        this.template.convertAndSend("q.order_in_delivery", new OrderInDeliveryEvent(orderDelivery.getOrderID()));
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order in delivery RabbitMQ event for order# "  + orderDelivery.getOrderID() + " published!");

    }
}
