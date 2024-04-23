package at.kolleg.erplite.sharedkernel.events;

import java.io.Serializable;

public record OrderPackedEvent(String orderId) implements Serializable {
}
