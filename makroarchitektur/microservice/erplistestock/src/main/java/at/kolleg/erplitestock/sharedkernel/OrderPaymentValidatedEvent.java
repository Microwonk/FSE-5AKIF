package at.kolleg.erplitestock.sharedkernel;

import java.io.Serializable;

public record OrderPaymentValidatedEvent(OrderResponse orderResponse) implements Serializable {
}
