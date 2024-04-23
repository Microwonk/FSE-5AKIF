package at.kolleg.erplite.sharedkernel.responses;

import at.kolleg.erplite.ordermanagement.marker.ValueObjectMarker;

import java.io.Serializable;

@ValueObjectMarker
public record LineItemResponse(
        String productNumber,
        String productName,
        double priceNet,
        int tax,
        int amount
) implements Serializable {
}


