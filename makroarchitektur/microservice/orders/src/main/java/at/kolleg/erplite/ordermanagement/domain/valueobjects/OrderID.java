package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import at.kolleg.erplite.ordermanagement.marker.ValueObjectMarker;

import java.io.Serializable;

@ValueObjectMarker
public record OrderID(String id) implements Serializable {
    public OrderID {
        if (!isValid(id))
            throw new IllegalArgumentException("ID must not be blank or less than 10 characters!");
    }

    public static boolean isValid(String name) {
        return name != null && name.length() == 10;
    }
}
