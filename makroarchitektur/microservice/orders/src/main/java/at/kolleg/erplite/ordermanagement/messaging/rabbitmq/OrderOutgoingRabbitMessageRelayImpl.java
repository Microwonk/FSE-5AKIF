package at.kolleg.erplite.ordermanagement.messaging.rabbitmq;

import at.kolleg.erplite.ordermanagement.marker.AdapterMarker;
import at.kolleg.erplite.ordermanagement.ports.out.OrderOutgoingMessageRelay;
import at.kolleg.erplite.sharedkernel.events.OrderInitiateDeliveryEvent;
import at.kolleg.erplite.sharedkernel.events.OrderPaymentValidatedEvent;
import at.kolleg.erplite.sharedkernel.events.OrderPlacedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
class OrderOutgoingRabbitMessageRelayImpl implements OrderOutgoingMessageRelay {

    @Autowired
    private RabbitTemplate template;

    /*
    @Qualifier("orderPlaced")
    @Autowired
    private Queue orderPlacedQueue;

    @Qualifier("orderPaymentCheck")
    @Autowired
    private Queue orderPaymentCheckQueue;*/

    @Override
    public void publish(final OrderPlacedEvent orderPlacedEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Publishing order placed RabbitMQ event for order# " + orderPlacedEvent.orderResponse().orderID());
        this.template.convertAndSend("q.order_placed", orderPlacedEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order placed event for order# " + orderPlacedEvent.orderResponse().orderID() + " published!");

    }

    @Override
    public void publish(final OrderPaymentValidatedEvent orderPaymentValidatedEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Publishing order payment validated RabbitMQ event");
        this.template.convertAndSend("q.order_paymentchecked", orderPaymentValidatedEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order payment validated event published!");
    }

    @Override
    public void publish(final OrderInitiateDeliveryEvent orderInitiateDeliveryEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Publishing orderInitiateDelivery RabbitMQ event!");
        this.template.convertAndSend("q.initiate_delivery", orderInitiateDeliveryEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "OrderInitiateDelivery event published!");
    }
}
