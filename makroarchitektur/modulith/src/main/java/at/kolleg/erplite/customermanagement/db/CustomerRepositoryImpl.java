package at.kolleg.erplite.customermanagement.db;

import at.kolleg.erplite.customermanagement.domain.Customer;
import at.kolleg.erplite.customermanagement.domain.CustomerID;
import at.kolleg.erplite.customermanagement.ports.out.CustomerRepository;
import at.kolleg.erplite.sharedkernel.marker.AdapterMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@AdapterMarker
class CustomerRepositoryImpl implements CustomerRepository {

    private CustomerJPARepository customerJPARepository;

    @Autowired
    public CustomerRepositoryImpl(CustomerJPARepository customerJPARepository) {
        this.customerJPARepository = customerJPARepository;
    }

    @Override
    public Optional<Customer> insert(Customer domainObject) {
        CustomerDbEntity customerDbEntity = customerJPARepository.save(DbMapperService.toOrm(domainObject));
        if (customerDbEntity == null) return Optional.empty();
        return Optional.of(DbMapperService.toDomain(customerDbEntity));
    }

    @Override
    public Optional<Customer> getById(CustomerID id) {
        Optional<CustomerDbEntity> optionalCustomerEntity = this.customerJPARepository.findById(id.id());
        if (!optionalCustomerEntity.isPresent()) return Optional.empty();
        return Optional.of(DbMapperService.toDomain(optionalCustomerEntity.get()));
    }

    @Override
    public List<Customer> getAll() {
        List<CustomerDbEntity> list = this.customerJPARepository.findAll();
        if (list == null) return Collections.emptyList();
        return list.stream().map(ormModel -> DbMapperService.toDomain(ormModel)).collect(Collectors.toList());
    }
    

    @Override
    public void deleteById(CustomerID id) {
        this.customerJPARepository.deleteById(id.id());
    }


}
