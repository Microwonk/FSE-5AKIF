package at.kolleg.erplite.customermanagement.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
class AddressDbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 1)
    private String street;
    @NotNull
    @Size(min = 1)
    private String zipcode;
    @NotNull
    @Size(min = 1)
    private String city;
    @NotNull
    @Size(min = 1)
    private String country;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private CustomerDbEntity customer;
    @Version
    private long version; //For optimistic Locking

    public AddressDbEntity(String street, String zipcode, String city, String country, CustomerDbEntity customerDbEntity) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
        this.customer = customerDbEntity;
    }
}
