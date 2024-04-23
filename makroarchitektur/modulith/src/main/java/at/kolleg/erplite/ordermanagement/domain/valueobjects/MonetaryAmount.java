package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record MonetaryAmount(BigDecimal amount) {

    public MonetaryAmount(BigDecimal amount) {
        if (!isValid(amount))
            throw new IllegalArgumentException("Price must be positive!");
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public MonetaryAmount add(MonetaryAmount b) {
        return new MonetaryAmount(this.amount.add(b.amount).setScale(2, RoundingMode.HALF_UP));
    }

    public MonetaryAmount subtract(MonetaryAmount b) {
        return new MonetaryAmount(this.amount.subtract(b.amount).setScale(2, RoundingMode.HALF_UP));
    }

    public MonetaryAmount multiply(MonetaryAmount b) {
        return new MonetaryAmount(this.amount.multiply(b.amount).setScale(2, RoundingMode.HALF_UP));
    }

    public MonetaryAmount divide(MonetaryAmount b) {
        return new MonetaryAmount(this.amount.divide(b.amount).setScale(2, RoundingMode.HALF_UP));
    }


    public MonetaryAmount add(BigDecimal b) {
        return new MonetaryAmount(this.amount.add(b.setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
    }

    public MonetaryAmount subtract(BigDecimal b) {
        return new MonetaryAmount(this.amount.subtract(b.setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
    }

    public MonetaryAmount multiply(BigDecimal b) {
        return new MonetaryAmount(this.amount.multiply(b.setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
    }

    public MonetaryAmount divide(BigDecimal b) {
        return new MonetaryAmount(this.amount.divide(b.setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
    }

    public static boolean isValid(BigDecimal amount) {
        return amount != null && amount.doubleValue() >= 0;
    }
}
