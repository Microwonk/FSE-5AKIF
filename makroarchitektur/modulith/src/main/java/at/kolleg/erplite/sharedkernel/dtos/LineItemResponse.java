package at.kolleg.erplite.sharedkernel.dtos;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

@ValueObjectMarker
public record LineItemResponse(
        String productNumber,
        String productName,
        double priceNet,
        int tax,
        int amount
) {
}


