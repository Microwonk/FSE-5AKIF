package at.kolleg.erplite.customermanagement.db;

import at.kolleg.erplite.customermanagement.domain.Address;
import at.kolleg.erplite.customermanagement.domain.Customer;
import at.kolleg.erplite.customermanagement.domain.CustomerID;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.Email;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.Name;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

class DbMapperService {

    public static Customer toDomain(@Valid CustomerDbEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Customer dto must not be null for conversion to ORM-Model!");

        List<Address> addressListDomain = new ArrayList<>();

        for (AddressDbEntity addressDbEntity : entity.getAddressList()) {
            addressListDomain.add(
                    new Address(addressDbEntity.getStreet(), addressDbEntity.getZipcode(), addressDbEntity.getCity(), addressDbEntity.getCountry())
            );
        }

        return new Customer(new CustomerID(entity.getId()),
                new Name(entity.getFirstname()),
                new Name(entity.getLastname()),
                new Email(entity.getEmail()),
                addressListDomain);
    }

    public static CustomerDbEntity toOrm(@Valid Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer must not be null for conversion in ORM!");

        CustomerDbEntity customerDbEntity = new CustomerDbEntity(
                customer.getCustomerID().id(),
                customer.getFirstname().name(),
                customer.getLastname().name(),
                customer.getEmail().email()
        );

        List<AddressDbEntity> addressDbEntityList = new ArrayList<>();
        for (Address addressDomainObject : customer.getAddressList()) {
            addressDbEntityList.add(new AddressDbEntity(addressDomainObject.street(), addressDomainObject.zipcode(), addressDomainObject.city(), addressDomainObject.country(), customerDbEntity));
        }
        customerDbEntity.setAddressList(addressDbEntityList);
        return customerDbEntity;
    }
}