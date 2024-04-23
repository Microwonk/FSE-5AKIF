package at.kolleg.erplite.ordermanagement.ports.in;


import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPackedEvent;

public interface OrderIncomingMessagesPort {
    void handle(OrderPackedEvent orderPackedEvent);
}
