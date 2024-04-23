package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

@ValueObjectMarker
public record ProductNumber(String number) {
    public ProductNumber {
        if (!isValid(number))
            throw new IllegalArgumentException("Product number must not be blank or less than 10 characters!");
    }

    public static boolean isValid(String number) {
        return number != null && number.length() == 10;
    }
}
