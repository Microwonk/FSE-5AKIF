package at.kolleg.erplite.ordermanagement.ports.out;

import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPaymentValidatedEvent;
import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPlacedEvent;
import at.kolleg.erplite.sharedkernel.marker.OutputPortMarker;

@OutputPortMarker
public interface OrderOutgoingMessageRelay {
    void publish(OrderPlacedEvent orderPlacedEvent);

    void publish(OrderPaymentValidatedEvent orderPayedEvent);
}
