package at.itkolleg.delivery.sharedkernel;

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
