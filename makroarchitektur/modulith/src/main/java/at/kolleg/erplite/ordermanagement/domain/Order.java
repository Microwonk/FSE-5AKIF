package at.kolleg.erplite.ordermanagement.domain;

import at.kolleg.erplite.ordermanagement.domain.valueobjects.MonetaryAmount;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderID;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderState;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderStateChangeNotPossibleException;
import at.kolleg.erplite.sharedkernel.marker.AggregateMarker;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
@AggregateMarker
public final class Order {

    //ID
    private final OrderID orderID;

    //Customer-Data
    private CustomerData customerData;

    //Line-Items
    private final List<LineItem> lineItems;

    //Order-State and calcuated values
    private OrderState state;
    private final MonetaryAmount taxTotal;
    private final MonetaryAmount netTotal;
    private final MonetaryAmount grossTotal;
    private final LocalDateTime date;

    public Order(OrderID orderID, CustomerData customerData,
                 LocalDateTime date, List<LineItem> lineItems, OrderState status) {
        if (orderID == null) throw new IllegalArgumentException("OrderID must not be null!");
        this.orderID = orderID;
        if (customerData == null)
            throw new IllegalArgumentException("Customer-Data for order invalid!");
        this.customerData = customerData;
        if (lineItems == null)
            throw new IllegalArgumentException("LineItems-List must not be null, at least an empy List is needed!");
        this.lineItems = lineItems;
        if (status == null) throw new IllegalArgumentException("State must not be null!");
        this.state = status;
        this.taxTotal = this.calculateOrderTax();
        this.netTotal = this.calculateOrderNetTotal();
        this.grossTotal = this.calculateOrderGrossTotal();
        this.date = date;
    }

    public List<LineItem> getLineItems() {
        return Collections.unmodifiableList(this.lineItems);
    }

    public void orderStateTransitionTo(OrderState newState) {
        switch (newState) {
            case CANCELED -> {
                if (this.state == OrderState.IN_DELIVERY || this.state == OrderState.DELIVERED)
                    throw new OrderStateChangeNotPossibleException("Order in State " + this.state + " cannot be canceled");
                this.state = newState;
            }
            case PAYMENT_VERIFIED -> {
                if (this.state != OrderState.PLACED)
                    throw new OrderStateChangeNotPossibleException("Order must be in state PLACED for transition to PAYMENT VERIFIED!");
                this.state = newState;
            }
            case PREPARING_FOR_DELIVERY -> {
                if (this.state != OrderState.PAYMENT_VERIFIED)
                    throw new OrderStateChangeNotPossibleException("Order must be in state PAYMENT VERIFED for transition to PREPARING FOR DELIVERY!");
                this.state = newState;
            }
            case IN_DELIVERY -> {
                if (this.state != OrderState.PREPARING_FOR_DELIVERY)
                    throw new OrderStateChangeNotPossibleException("Order must be in state PREPARING FOR DELIVERY for transition to IN DELIVERY!");
                this.state = newState;
            }
            case DELIVERED -> {
                if (this.state != OrderState.IN_DELIVERY)
                    throw new OrderStateChangeNotPossibleException("Order must be in state IN DELIVERY for transition to DELIVERED!");
                this.state = newState;
            }
        }
    }

    private MonetaryAmount calculateOrderTax() {
        MonetaryAmount orderTax = new MonetaryAmount(new BigDecimal(0));
        for (LineItem lineItem : this.lineItems) {

            orderTax = orderTax.add(lineItem.getTotalTaxLine());
        }
        return orderTax;
    }

    private MonetaryAmount calculateOrderNetTotal() {
        MonetaryAmount orderNetSum = new MonetaryAmount(new BigDecimal(0));
        for (LineItem lineItem : this.lineItems) {
            orderNetSum = orderNetSum.add(lineItem.getTotalNetLine());
        }
        return orderNetSum;
    }

    private MonetaryAmount calculateOrderGrossTotal() {
        return this.netTotal.add(this.taxTotal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return getOrderID().equals(order.getOrderID());
    }

    @Override
    public int hashCode() {
        return getOrderID().hashCode();
    }


}