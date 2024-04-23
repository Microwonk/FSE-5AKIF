package at.kolleg.erplite.customermanagement.ports.in;

import at.kolleg.erplite.sharedkernel.marker.InputPortMarker;
import at.kolleg.erplite.customermanagement.domain.Customer;
import at.kolleg.erplite.customermanagement.domain.CustomerID;

@InputPortMarker
public interface CustomerQueriesService {
    Customer getCustomerById(CustomerID customerID);
}
