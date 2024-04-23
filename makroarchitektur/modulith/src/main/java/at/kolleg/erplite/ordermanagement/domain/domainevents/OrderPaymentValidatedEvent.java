package at.kolleg.erplite.ordermanagement.domain.domainevents;

import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;

public record OrderPaymentValidatedEvent(OrderResponse orderResponse) {
}
