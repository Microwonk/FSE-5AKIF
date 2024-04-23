package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

@ValueObjectMarker
public record OrderPosition(int orderPosition) {
    public OrderPosition {
        if (!isValid(orderPosition)) throw new IllegalArgumentException("Order position must not be positive integer!");
    }

    public static boolean isValid(int amount) {
        return amount >= 0;
    }
}
