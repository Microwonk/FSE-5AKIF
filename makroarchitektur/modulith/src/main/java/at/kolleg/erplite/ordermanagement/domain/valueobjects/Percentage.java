package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

@ValueObjectMarker
public record Percentage(int percentage) {
    public Percentage {
        if (!isValid(percentage))
            throw new IllegalArgumentException("Percentage must be between 0 and 100!");
    }

    public static boolean isValid(int percentage) {
        return percentage >= 0 && percentage <= 100;
    }
}
