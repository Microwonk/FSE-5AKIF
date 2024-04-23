package at.kolleg.erplite.ordermanagement.services.exceptions;

public class OrderInitiateDeliveryException extends RuntimeException {
    public OrderInitiateDeliveryException(String message) {
        super("Order could not be prepared for delivery!" + message);
    }
}
