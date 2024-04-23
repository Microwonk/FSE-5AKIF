package at.kolleg.erplite.customermanagement.domain;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

@ValueObjectMarker
public record CustomerID(String id) {
    public CustomerID {
        if (!CustomerID.isValid(id))
            throw new IllegalArgumentException("Customer ID must not be blank and has to have at least 10 characters!");
    }

    public static boolean isValid(String id) {
        return id != null && id.length() == 10;
    }
}
