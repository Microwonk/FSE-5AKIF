package at.kolleg.erplite.ordermanagement.db;

import at.kolleg.erplite.ordermanagement.domain.Order;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderID;
import at.kolleg.erplite.ordermanagement.ports.out.OrderRepository;
import at.kolleg.erplite.sharedkernel.marker.RepositoryMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RepositoryMarker
@Repository
class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Override
    public Optional<Order> insert(Order order) {
        if (order == null) throw new IllegalArgumentException("Order to be inserted must not be null!");
        OrderDbEntity orderDbEntity = DbOrderMapperService.toOrm(order);
        OrderDbEntity insertedEntity = orderJPARepository.save(orderDbEntity);
        if (insertedEntity == null) return Optional.empty();
        return Optional.of(DbOrderMapperService.toDomain(insertedEntity));
    }

    @Override
    public Optional<Order> getById(OrderID id) {
        if (id == null) throw new IllegalArgumentException("OrderID for Order to get from db must not be null!");
        Optional<OrderDbEntity> orderEntityOptional = this.orderJPARepository.findById(id.id());
        if (!orderEntityOptional.isPresent()) return Optional.empty();
        return Optional.of(DbOrderMapperService.toDomain(orderEntityOptional.get()));
    }

    @Override
    public List<Order> getAll() {
        List<OrderDbEntity> list = this.orderJPARepository.findAll();
        if (list == null) return Collections.emptyList();
        return list.stream().map(dbEntity -> DbOrderMapperService.toDomain(dbEntity)).toList();
    }


    @Override
    public void deleteById(OrderID id) {
        this.orderJPARepository.deleteById(id.id());
    }

    @Override
    public List<Order> getAllSortedAndPaged(int page, int pageSize, String sortedBy) {
        Pageable sortedAndPagedOrders = PageRequest.of(page, pageSize, Sort.by(sortedBy));
        Page<OrderDbEntity> resultPage = this.orderJPARepository.findAll(sortedAndPagedOrders);
        if (resultPage.isEmpty()) return Collections.emptyList();
        return resultPage.stream().map(dbEntity -> DbOrderMapperService.toDomain(dbEntity)).toList();
    }


    /* //Alternative zum untenstehenden Aufruf, der über eine eigene Update-Methode arbeitet.
    @Override
    public void updateOrderWithNewState(Order order) {
        this.orderJPARepository.updateOrderState(order.getOrderID().id(), order.getState());
    }
    */

    @Override
    public void updateOrderWithNewState(Order order) { //first read the order, then change the entity object, then persist the order with save-call (transaction management included)
        Optional<OrderDbEntity> optOrderDbEntity = this.orderJPARepository.findById(order.getOrderID().id());
        if (optOrderDbEntity.isPresent()) {
            OrderDbEntity orderDbEntity = optOrderDbEntity.get();
            orderDbEntity.setState(order.getState());//set state
            //further field could be updated if necessary (in further methods or in one Method containing all changes)
            this.orderJPARepository.save(orderDbEntity);
        }

    }
}