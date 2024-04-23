package at.kolleg.erplite.sharedkernel.commands;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record OrderPaymentCheckCommand(
        @NotNull @Size(min = 10, max = 10, message = "Customer ID must have a length of 10!") String orderID) {

    public OrderPaymentCheckCommand {
        if (orderID == null) throw new IllegalArgumentException("OrderID for checking payment status not valid!");
    }
}

