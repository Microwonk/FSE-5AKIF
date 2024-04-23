package at.kolleg.erplite.customermanagement.usecases;

import at.kolleg.erplite.customermanagement.domain.Customer;
import at.kolleg.erplite.customermanagement.domain.CustomerID;
import at.kolleg.erplite.customermanagement.ports.in.CustomerQueriesService;
import at.kolleg.erplite.customermanagement.ports.out.CustomerRepository;
import at.kolleg.erplite.sharedkernel.marker.UseCaseMarker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@UseCaseMarker
@AllArgsConstructor
public class CustomerQueryServiceService implements CustomerQueriesService {

    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(CustomerID customerID) {

        return null;
    }
}
