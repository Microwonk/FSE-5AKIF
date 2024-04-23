package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

@ValueObjectMarker
public record Amount(int amount) {
    public Amount {
        if (!isValid(amount)) throw new IllegalArgumentException("Amount must not be positive integer!");
    }

    public static boolean isValid(int amount) {
        return amount >= 0;
    }
}
