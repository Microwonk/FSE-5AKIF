package at.kolleg.erplite.sharedkernel.commands;

import at.kolleg.erplite.sharedkernel.dtos.CartItem;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public record PlaceOrderCommand(
        @NotNull @Size(min = 10, max = 10, message = "Customer ID must have a length of 10!") String customerID,
        @NotNull @Size(min = 1, message = "Customer firstname name must not be null and must have at least 1 Character!") String customerFirstname,
        @NotNull @Size(min = 1, message = "Customer lastname  must not be null and must have at least 1 Character!") String customerLastname,
        @Email String customerEmail,
        @NotNull @Size(min = 1, message = "Customer street must not be null and must have at least 1 Character!") String customerStreet,
        @NotNull @Size(min = 1, message = "Customer zipcode must not be null and must have at least 1 Character!") String customerZipcode,
        @NotNull @Size(min = 1, message = "Customer city must not be null and must have at least 1 Character!") String customerCity,
        @NotNull @Size(min = 1, message = "Customer country must not be null and must have at least 1 Character!") String customerCountry,
        @Valid @NotNull List<CartItem> cartItems) { //Valdation cascading for Javax-Validation with @Valid

    public PlaceOrderCommand {
        if (customerID == null) throw new IllegalArgumentException("CustomerID for placing order not valid!");

        if (customerFirstname == null)
            throw new IllegalArgumentException("Customer firstname for placing order not valid!");

        if (customerLastname == null)
            throw new IllegalArgumentException("Customer lastname for placing order not valid!");

        if (customerEmail == null) throw new IllegalArgumentException("Customer email for placing order not valid!");

        if (customerStreet == null) throw new IllegalArgumentException("Customer street for placing order not valid!");

        if (customerZipcode == null)
            throw new IllegalArgumentException("Customer zipcode for placing order not valid!");

        if (customerCity == null) throw new IllegalArgumentException("Customer city for placing order not valid!");

        if (customerCountry == null)
            throw new IllegalArgumentException("Customer country for placing order not valid!");

        if (cartItems == null) throw new IllegalArgumentException("Ordered iteams for placing order not valid!");

    }
}
