package at.kolleg.erplite.customermanagement.domain;

import at.kolleg.erplite.sharedkernel.marker.AggregateMarker;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.Email;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.Name;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@AggregateMarker
public class Customer {

    private final CustomerID customerID;
    private Name firstname;
    private Name lastname;
    private Email email;
    private final List<Address> addressList;

    public Customer(CustomerID customerID, Name firstname, Name lastname, Email email, List<Address> addressList) {
        if (customerID == null) throw new IllegalArgumentException("CustomerID must not be blank or null!");
        this.customerID = customerID;
        if (firstname == null) throw new IllegalArgumentException("Firstname must not be blank or null!");
        this.firstname = firstname;
        if (lastname == null) throw new IllegalArgumentException("Lastname must not be blank or null!");
        this.lastname = lastname;
        if (email == null) throw new IllegalArgumentException("Email must not be null!");
        this.email = email;
        if (addressList == null) {
            this.addressList = new ArrayList<>();
        } else {
            this.addressList = addressList;
        }
    }

    public void changeFirstName(Name newName) {
        if (newName == null) throw new IllegalArgumentException("Firstname must not be null!");
        this.firstname = newName;
    }

    public void changeLastName(Name newName) {
        if (newName == null) throw new IllegalArgumentException("Lastname must not be null!");
        this.lastname = newName;
    }

    public void changeEmail(Email newEmail) {
        if (newEmail == null) throw new IllegalArgumentException("Email must not be null!");
        this.email = newEmail;
    }

    public void addAddress(Address newAddress) {
        if (newAddress == null) throw new IllegalArgumentException("Address must not be null!");
        this.addressList.add(newAddress);
    }

    public List<Address> getAddressList() {
        return Collections.unmodifiableList(this.addressList);
    }

    public Address getAddressOnListPosition(int position) {
        if (position < 0 || position > this.addressList.size() - 1)
            throw new IllegalArgumentException("Inavlid Address-List position: " + position);
        return this.addressList.get(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        return customerID.equals(customer.customerID);
    }

    @Override
    public int hashCode() {
        return customerID.hashCode();
    }
}
