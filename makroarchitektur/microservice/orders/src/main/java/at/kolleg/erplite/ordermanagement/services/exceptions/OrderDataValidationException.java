package at.kolleg.erplite.ordermanagement.services.exceptions;

import java.util.List;

public class OrderDataValidationException extends RuntimeException {
    public OrderDataValidationException(List<String> errors) {
        super("Validation exception: " + errors);
    }
}
