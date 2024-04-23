package at.kolleg.erplitestock.sharedkernel;

import java.io.Serializable;


public record LineItemResponse(
        String productNumber,
        String productName,
        double priceNet,
        int tax,
        int amount
) implements Serializable {
}


