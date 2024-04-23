package at.kolleg.erplite.ordermanagement.services.exceptions;


public class OrderPlacementNotSuccessfullException extends RuntimeException {
    public OrderPlacementNotSuccessfullException(String message) {

        super("Order could not be placed: " + message);
    }
}
