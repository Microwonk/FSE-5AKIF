package at.kolleg.erplite.ordermanagement.messaging.spring;

import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPaymentValidatedEvent;
import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPlacedEvent;
import at.kolleg.erplite.ordermanagement.ports.out.OrderOutgoingMessageRelay;
import at.kolleg.erplite.sharedkernel.events.OrderPaymentValidatedSpringEvent;
import at.kolleg.erplite.sharedkernel.events.OrderPlacedSpringEvent;
import at.kolleg.erplite.sharedkernel.marker.AdapterMarker;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@AdapterMarker
@Service
@AllArgsConstructor
class OrderOutgoingSpringMessageRelayImpl implements OrderOutgoingMessageRelay {
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(final OrderPlacedEvent orderPlacedEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order placed domain event published for order# " + orderPlacedEvent.orderResponse().orderID());
        OrderPlacedSpringEvent orderPlacedSpringEvent = new OrderPlacedSpringEvent(this, orderPlacedEvent.orderResponse());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Publishing order placed spring event for order# " + orderPlacedEvent.orderResponse().orderID());
        applicationEventPublisher.publishEvent(orderPlacedSpringEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order placed spring event for order# " + orderPlacedEvent.orderResponse().orderID() + " published!");

    }

    @Override
    public void publish(final OrderPaymentValidatedEvent orderPaymentValidatedEvent) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order payment validated domain event ...");
        OrderPaymentValidatedSpringEvent orderPaymentValidatedSpringEvent = new OrderPaymentValidatedSpringEvent(this, orderPaymentValidatedEvent.orderResponse());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Publishing order payment validated spring event");
        applicationEventPublisher.publishEvent(orderPaymentValidatedSpringEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order payment validated spring event published!");
    }
}
