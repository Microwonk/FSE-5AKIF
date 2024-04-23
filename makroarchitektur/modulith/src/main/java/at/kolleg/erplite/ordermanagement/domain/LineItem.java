package at.kolleg.erplite.ordermanagement.domain;

import at.kolleg.erplite.ordermanagement.domain.valueobjects.*;
import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode
@ToString
@ValueObjectMarker
public final class LineItem {
    private final OrderPosition orderPosition;
    private final ProductNumber productNumber;
    private final Name productName;
    private final MonetaryAmount priceNet;
    private final Percentage tax;
    private final Amount amount;

    //calculated fields
    private final MonetaryAmount totalNetLine;
    private final MonetaryAmount totalTaxLine;
    private final MonetaryAmount totalGrossLine;

    public LineItem(OrderPosition orderPosition, ProductNumber productNumber, Name productName, MonetaryAmount priceNet, Percentage tax, Amount amount) {
        this.orderPosition = orderPosition;
        this.productNumber = productNumber;
        this.productName = productName;
        this.priceNet = new MonetaryAmount(priceNet.amount().setScale(2, RoundingMode.HALF_UP));
        this.tax = tax;
        this.amount = amount;
        this.totalNetLine = this.priceNet.multiply(new BigDecimal(this.amount.amount()));
        this.totalTaxLine = this.totalNetLine.multiply(new BigDecimal(this.tax.percentage()).divide(new BigDecimal(100)));
        this.totalGrossLine = this.totalNetLine.add(this.totalTaxLine);
    }

    public LineItem withNewAmount(Amount newAmount) {
        if (newAmount == null) throw new IllegalArgumentException("Amount must not be null / empty!");
        return new LineItem(this.orderPosition, this.productNumber, this.productName, this.priceNet, this.tax, newAmount);
    }

    public LineItem withNewNetPrice(MonetaryAmount newNetPrice) {
        if (newNetPrice == null) throw new IllegalArgumentException("Netprice must not be null / empty!");
        return new LineItem(this.orderPosition, this.productNumber, this.productName, newNetPrice, this.tax, this.amount);
    }
}
