package at.kolleg.erplite.sharedkernel.commands;

public record OrderInitiateDeliveryCommand(String orderID) {

    public OrderInitiateDeliveryCommand
    {
        if (orderID == null) throw new IllegalArgumentException("OrderID for placing order not valid!");
    }

}
