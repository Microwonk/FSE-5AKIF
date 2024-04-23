package at.kolleg.erplitestock.sharedkernel;

import java.io.Serializable;
import java.util.List;

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
) implements Serializable {

}