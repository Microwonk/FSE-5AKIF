package at.kolleg.erplite.ordermanagement.services.exceptions;

public class OrderStateChangeNotPossibleException extends RuntimeException {
    public OrderStateChangeNotPossibleException(String message) {
        super("Order state change not possible: " + message);
    }
}
