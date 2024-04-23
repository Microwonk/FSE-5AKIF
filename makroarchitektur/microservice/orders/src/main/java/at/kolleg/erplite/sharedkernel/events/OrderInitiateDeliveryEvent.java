package at.kolleg.erplite.sharedkernel.events;

import java.io.Serializable;

public record OrderInitiateDeliveryEvent(String orderID,
                                         String customerID,
                                         String customerFirstname,
                                         String customerLastname,
                                         String customerEmail,
                                         String customerStreet,
                                         String customerZipcode,
                                         String customerCity,
                                         String customerCountry) implements Serializable {
}
