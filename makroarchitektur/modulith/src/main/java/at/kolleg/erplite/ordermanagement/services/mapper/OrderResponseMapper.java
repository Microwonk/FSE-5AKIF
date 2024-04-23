package at.kolleg.erplite.ordermanagement.services.mapper;

import at.kolleg.erplite.ordermanagement.domain.LineItem;
import at.kolleg.erplite.ordermanagement.domain.Order;
import at.kolleg.erplite.sharedkernel.dtos.LineItemResponse;
import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;

import java.util.ArrayList;
import java.util.List;

public class OrderResponseMapper {

    public static OrderResponse toResponseFromDomain(Order order) {
        List<LineItemResponse> lineItemResponseList = new ArrayList<>();
        for (LineItem lineItem : order.getLineItems()) {
            lineItemResponseList.add(new LineItemResponse(
                    lineItem.getProductNumber().number(),
                    lineItem.getProductName().name(),
                    lineItem.getPriceNet().amount().doubleValue(),
                    lineItem.getTax().percentage(),
                    lineItem.getAmount().amount()
            ));
        }

        OrderResponse orderResponse = new OrderResponse(
                order.getOrderID().id(),
                order.getCustomerData().customerID().id(),
                order.getCustomerData().firstname().name(),
                order.getCustomerData().lastname().name(),
                order.getCustomerData().email().email(),
                order.getCustomerData().street(),
                order.getCustomerData().zipcode(),
                order.getCustomerData().city(),
                order.getCustomerData().country(),
                lineItemResponseList,
                order.getState().name(),
                order.getTaxTotal().amount().doubleValue(),
                order.getNetTotal().amount().doubleValue(),
                order.getGrossTotal().amount().doubleValue(),
                order.getDate().toString()
        );
        return orderResponse;

    }

}
