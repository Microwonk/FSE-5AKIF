package at.kolleg.erplite.customermanagement.ports.in;

import at.kolleg.erplite.customermanagement.domain.Customer;
import at.kolleg.erplite.customermanagement.usecases.commands.AddCustomerAddressCommand;
import at.kolleg.erplite.customermanagement.usecases.commands.CustomerCreationCommand;
import at.kolleg.erplite.customermanagement.usecases.commands.CustomerDeleteCommand;
import at.kolleg.erplite.customermanagement.usecases.commands.CustomerUpdateCommand;
import at.kolleg.erplite.sharedkernel.marker.InputPortMarker;

@InputPortMarker
public interface CustomerCommandsService {
    Customer handle(CustomerCreationCommand customerCreationCommand);

    Customer handle(CustomerUpdateCommand customerUpdateCommand);

    void handle(CustomerDeleteCommand customerDeleteCommand);

    void handle(AddCustomerAddressCommand customerAddressCommand);
}
