package at.itkolleg.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deliveryId;
    private String orderID;
    private String customerID;
    private String customerFirstname;
    private String customerLastname;
    private String customerEmail;
    private String customerStreet;
    private String customerZipcode;
    private String customerCity;
    private String customerCountry;
    private Boolean delivered;

    public void setDelivered()
    {
        this.delivered = true;
    }
}
