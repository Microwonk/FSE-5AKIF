package at.kolleg.erplite.ordermanagement.db;

import at.kolleg.erplite.customermanagement.domain.CustomerID;
import at.kolleg.erplite.ordermanagement.domain.CustomerData;
import at.kolleg.erplite.ordermanagement.domain.LineItem;
import at.kolleg.erplite.ordermanagement.domain.Order;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.*;

import java.util.ArrayList;
import java.util.List;


class DbOrderMapperService {

    public static Order toDomain(OrderDbEntity dbModel) {
        if (dbModel == null)
            throw new IllegalArgumentException("OrderEntity for conversion into DomainModel must not be null!");
        List<LineItem> lineItemList = new ArrayList<>();

        for (LineItemDbEntity item : dbModel.getLineItems()) {
            lineItemList.add(
                    new LineItem(
                            new OrderPosition((item.getOrderPosition())),
                            new ProductNumber(item.getProductNumber()),
                            new Name(item.getProductName()),
                            new MonetaryAmount(item.getPriceNet()),
                            new Percentage(item.getTax()),
                            new Amount(item.getAmount())
                    )
            );
        }

        return new Order(
                new OrderID(dbModel.getOrderID()),
                new CustomerData(
                        new CustomerID(dbModel.getOrderCustomerDetails().getCustomerId()),
                        new Name(dbModel.getOrderCustomerDetails().getFirstname()),
                        new Name(dbModel.getOrderCustomerDetails().getLastname()),
                        new Email(dbModel.getOrderCustomerDetails().getEmail()),
                        dbModel.getOrderCustomerDetails().getStreet(),
                        dbModel.getOrderCustomerDetails().getZipcode(),
                        dbModel.getOrderCustomerDetails().getCity(),
                        dbModel.getOrderCustomerDetails().getCountry()),
                dbModel.getDate(),
                lineItemList,
                dbModel.getState()
        );
    }

    public static OrderDbEntity toOrm(Order domainObject) {
        if (domainObject == null)
            throw new IllegalArgumentException("DomainObject for conversion into DB-Entity must not be null!");

        OrderDbEntity orderDbEntity = new OrderDbEntity(
                domainObject.getOrderID().id(),
                new OrderCustomerDetailsDbEntity(
                        domainObject.getCustomerData().customerID().id(),
                        domainObject.getCustomerData().firstname().name(),
                        domainObject.getCustomerData().lastname().name(),
                        domainObject.getCustomerData().email().email(),
                        domainObject.getCustomerData().street(),
                        domainObject.getCustomerData().zipcode(),
                        domainObject.getCustomerData().city(),
                        domainObject.getCustomerData().country()
                ),
                domainObject.getDate(),
                domainObject.getState(),
                domainObject.getTaxTotal().amount(),
                domainObject.getNetTotal().amount(),
                domainObject.getGrossTotal().amount()
        );

        List<LineItemDbEntity> lineItemDbEntityList = new ArrayList<>();

        for (LineItem item : domainObject.getLineItems()) {
            lineItemDbEntityList.add(new LineItemDbEntity(
                            item.getOrderPosition().orderPosition(),
                            item.getProductNumber().number(),
                            item.getProductName().name(),
                            item.getPriceNet().amount(),
                            item.getTax().percentage(),
                            item.getAmount().amount(),
                            item.getTotalNetLine().amount(),
                            item.getTotalTaxLine().amount(),
                            item.getTotalGrossLine().amount(),
                            orderDbEntity
                    )
            );
        }

        orderDbEntity.setLineItems(lineItemDbEntityList);

        return orderDbEntity;
    }
}