package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

@ValueObjectMarker
public record Name(String name) {
    public Name {
        if (!isValid(name)) throw new IllegalArgumentException("Name must not be blank or null!");
    }

    public static boolean isValid(String name) {
        return name != null && name.length() > 1;
    }
}
