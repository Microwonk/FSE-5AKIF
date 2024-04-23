package at.kolleg.erplite.ordermanagement.ports.in;


import at.kolleg.erplite.sharedkernel.events.OrderDeliveredEvent;
import at.kolleg.erplite.sharedkernel.events.OrderInDeliveryEvent;
import at.kolleg.erplite.sharedkernel.events.OrderPackedEvent;

public interface OrderIncomingMessagesPort {
    void handle(OrderPackedEvent orderPackedEvent);
    void handle(OrderDeliveredEvent orderDeliveredEvent);
    void handle(OrderInDeliveryEvent orderInDeliveryEvent);
}
