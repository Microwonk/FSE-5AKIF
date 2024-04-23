package at.kolleg.erplite.customermanagement.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
class CustomerDbEntity {
    @Id
    @NotNull
    @Size(min = 10, max = 10)
    private String id;
    @NotNull
    @Size(min = 1)
    private String firstname;
    @Size(min = 1)
    @NotNull
    private String lastname;
    @NotNull
    @Email
    private String email;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    private List<AddressDbEntity> addressList;

    public CustomerDbEntity(String id, String firstname, String lastname, String email, List<AddressDbEntity> addressList) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        if (addressList == null) this.addressList = new ArrayList<>();
        setAddressList(addressList);
    }

    public CustomerDbEntity(String id, String firstname, String lastname, String email) { //without Addresses
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.addressList = new ArrayList<>();
    }

    public void setAddressList(List<AddressDbEntity> addressDbEntityList) {
        if (addressDbEntityList != null) {
            this.addressList = addressDbEntityList;
        }
        for (AddressDbEntity addressDbEntity : this.addressList) { //change Owner of Address to this
            addressDbEntity.setCustomer(this);
        }
    }
}
