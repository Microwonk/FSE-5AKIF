package at.kolleg.erplite.ordermanagement.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
class OrderCustomerDetailsDbEntity {
    private String customerId;
    private String firstname;
    private String lastname;
    private String email;
    private String street;
    private String zipcode;
    private String city;
    private String country;
}
