package at.kolleg.erplite.sharedkernel.dtos;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

import java.util.List;

@ValueObjectMarker
public record OrderResponse(
        String orderID,
        String customerID,
        String customerFirstname,
        String customerLastname,
        String customerEmail,
        String customerStreet,
        String customerZipcode,
        String customerCity,
        String customerCountry,
        List<LineItemResponse> orderLineItems,
        String state,
        double taxTotal,
        double netTotal,
        double grossTotal,
        String date
) {

}