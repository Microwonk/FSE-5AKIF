package at.kolleg.erplitestock.sharedkernel;


import java.io.Serializable;

public record OrderPlacedEvent(OrderResponse orderResponse) implements Serializable {
}
