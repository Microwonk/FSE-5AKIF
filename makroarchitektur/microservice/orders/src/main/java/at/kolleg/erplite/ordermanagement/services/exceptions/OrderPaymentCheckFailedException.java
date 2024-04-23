package at.kolleg.erplite.ordermanagement.services.exceptions;


public class OrderPaymentCheckFailedException extends RuntimeException {
    public OrderPaymentCheckFailedException(String message) {

        super("Order payment check failed!" + message);
    }
}
