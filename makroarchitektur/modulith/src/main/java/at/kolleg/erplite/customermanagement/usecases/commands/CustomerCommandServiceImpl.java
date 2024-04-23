package at.kolleg.erplite.customermanagement.usecases.commands;

import at.kolleg.erplite.customermanagement.domain.Customer;
import at.kolleg.erplite.customermanagement.ports.in.CustomerCommandsService;
import at.kolleg.erplite.customermanagement.ports.out.CustomerRepository;
import at.kolleg.erplite.sharedkernel.marker.UseCaseMarker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@UseCaseMarker
@AllArgsConstructor
public class CustomerCommandServiceImpl implements CustomerCommandsService {

    CustomerRepository customerRepository;

    @Override
    public Customer handle(CustomerCreationCommand customerCreationCommand) {
        //TODO Logik für Fortlaufende Customer-Nummern

        //ZUerst eindeutige ID für Customers generieren (gehört zur Geschäftslogik)


        //Dann Customer-Objekte mit neuer ID generieren


        //Dann Customer-Objekt in DB schreiben


        //Gleichzeitig Outbox-Table befüllen --> Dort passiert dann das Event-Handling über das Outbox-Service
        return null;
    }

    @Override
    public Customer handle(CustomerUpdateCommand customerUpdateCommand) {
        ///  return customerRepository.update(DtoMapper.toDomainEntity(customerDto)).get();//TODO: Get noch afulösen
        return null;
    }

    @Override
    public void handle(CustomerDeleteCommand customerDeleteCommand) {

    }

    @Override
    public void handle(AddCustomerAddressCommand customerAddressCommand) {
     /*Optional<Customer> customer = customerRepository.getById(customerID);

        if (customer.isPresent()) {
            customer.get().addAddress(DtoMapper.toDomainEntity(addressDto));
        }*/
    }
}
