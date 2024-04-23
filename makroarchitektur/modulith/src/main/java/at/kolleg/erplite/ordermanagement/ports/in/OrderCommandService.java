package at.kolleg.erplite.ordermanagement.ports.in;

import at.kolleg.erplite.ordermanagement.services.exceptions.OrderPaymentCheckFailedException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderPlacementNotSuccessfullException;
import at.kolleg.erplite.sharedkernel.commands.OrderPaymentCheckCommand;
import at.kolleg.erplite.sharedkernel.commands.PlaceOrderCommand;
import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;

public interface OrderCommandService {
    OrderResponse handle(PlaceOrderCommand placeOrderCommand) throws OrderPlacementNotSuccessfullException;

    void handle(OrderPaymentCheckCommand orderPaymentCheckCommand) throws OrderPaymentCheckFailedException;
}
