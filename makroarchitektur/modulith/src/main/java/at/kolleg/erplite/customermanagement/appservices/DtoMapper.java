package at.kolleg.erplite.customermanagement.appservices;

public class DtoMapper {
/*
    public static Customer toDomainEntity(@Valid CustomerDto customerDto) {
        if (customerDto == null)
            throw new IllegalArgumentException("Customer dto must not be null for conversion to ORM-Model!");

        ArrayList<Address> addressList = new ArrayList<>();

        for (AddressDto addressEntity : customerDto.getAddressList()) {
            addressList.add(
                    new Address(addressEntity.getStreet(),
                            addressEntity.getZipcode(),
                            addressEntity.getCity(),
                            addressEntity.getCountry())
            );
        }
        return new Customer(new CustomerID(customerDto.getId()),
                new Name(customerDto.getFirstname()),
                new Name(customerDto.getLastname()),
                new Email(customerDto.getEmail()),
                addressList);
    }

    public static CustomerDto toDto(@Valid Customer customerDomainEntity) {
        if (customerDomainEntity == null)
            throw new IllegalArgumentException("Customer must not be null for conversion in ORM!");

        ArrayList<AddressDto> entityAddresses = new ArrayList<>();
        for (Address address : customerDomainEntity.getAddressList()) {
            entityAddresses.add(new AddressDto(address.street(), address.zipcode(), address.city(), address.country()));
        }

        return new CustomerDto(customerDomainEntity.getCustomerID().id(),
                customerDomainEntity.getFirstname().name(),
                customerDomainEntity.getLastname().name(),
                customerDomainEntity.getEmail().email(),
                entityAddresses);
    }

    public static AddressDto toDto(@Valid Address addressDomainEntity) {
        return new AddressDto(addressDomainEntity.street(), addressDomainEntity.zipcode(), addressDomainEntity.city(), addressDomainEntity.country());
    }

    public static Address toDomainEntity(@Valid AddressDto addressDto) {
        return new Address(addressDto.getStreet(), addressDto.getZipcode(), addressDto.getCity(), addressDto.getCountry());
    }

 */
}
