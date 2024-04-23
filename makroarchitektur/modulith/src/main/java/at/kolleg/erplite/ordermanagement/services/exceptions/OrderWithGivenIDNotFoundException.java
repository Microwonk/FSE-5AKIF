package at.kolleg.erplite.ordermanagement.services.exceptions;


public class OrderWithGivenIDNotFoundException extends RuntimeException {
    public OrderWithGivenIDNotFoundException(String message) {
        super("OrderId Not Found: " + message);
    }
}
