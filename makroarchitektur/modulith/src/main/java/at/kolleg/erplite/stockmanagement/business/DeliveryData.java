package at.kolleg.erplite.stockmanagement.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryData {
    public String name;
    public String street;
    public String zipcode;
    public String city;
    public String country;
}
