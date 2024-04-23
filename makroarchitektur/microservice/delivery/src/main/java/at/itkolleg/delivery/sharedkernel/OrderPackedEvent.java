package at.itkolleg.delivery.sharedkernel;

import java.io.Serializable;

public record OrderPackedEvent(String orderId) implements Serializable {
}
