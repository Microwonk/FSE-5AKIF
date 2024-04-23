package at.kolleg.erplite.ordermanagement.domain;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;
import at.kolleg.erplite.customermanagement.domain.CustomerID;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.Email;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.Name;

@ValueObjectMarker
public record CustomerData(
        CustomerID customerID,
        Name firstname,
        Name lastname,
        Email email,
        String street,
        String zipcode,
        String city,
        String country
) {
    public CustomerData {
        if (customerID == null) throw new IllegalArgumentException("Customer Id must not be null for orders!");
        if (firstname == null) throw new IllegalArgumentException("Firstname must not be null for orders!");
        if (lastname == null) throw new IllegalArgumentException("Lastnamde  must not be null for orders!");
        if (email == null) throw new IllegalArgumentException("Email-Adress must not be null for orders!");
        if (street == null) throw new IllegalArgumentException("Street must not be null for orders!");
        if (zipcode == null) throw new IllegalArgumentException("Zipcode must not be null for orders!");
        if (city == null) throw new IllegalArgumentException("City must not be null for orders!");
        if (country == null) throw new IllegalArgumentException("Country must not be null for orders!");

    }
}