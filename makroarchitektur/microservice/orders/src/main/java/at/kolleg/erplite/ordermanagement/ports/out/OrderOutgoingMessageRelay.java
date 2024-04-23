package at.kolleg.erplite.ordermanagement.ports.out;

import at.kolleg.erplite.ordermanagement.marker.OutputPortMarker;
import at.kolleg.erplite.sharedkernel.events.OrderInitiateDeliveryEvent;
import at.kolleg.erplite.sharedkernel.events.OrderPaymentValidatedEvent;
import at.kolleg.erplite.sharedkernel.events.OrderPlacedEvent;

@OutputPortMarker
public interface OrderOutgoingMessageRelay {
    void publish(OrderPlacedEvent orderPlacedEvent);
    void publish(OrderPaymentValidatedEvent orderPayedEvent);
    void publish(OrderInitiateDeliveryEvent orderInitiateDeliveryEvent);
}
