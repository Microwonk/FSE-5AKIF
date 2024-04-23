package at.kolleg.erplite.ordermanagement.api;

import java.util.Collections;
import java.util.Map;

final class OrderPlacedFieldValidationException extends RuntimeException {
    private Map<String, String> validationErrors;

    public OrderPlacedFieldValidationException(String message, Map<String, String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return Collections.unmodifiableMap(validationErrors);
    }
}
