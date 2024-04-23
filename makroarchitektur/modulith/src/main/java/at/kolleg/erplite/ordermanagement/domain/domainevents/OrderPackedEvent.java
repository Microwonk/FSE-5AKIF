package at.kolleg.erplite.ordermanagement.domain.domainevents;

import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderID;

public record OrderPackedEvent(OrderID orderId) {
}
