package at.kolleg.erplite.stockmanagement.adapter;

import at.kolleg.erplite.sharedkernel.dtos.LineItemResponse;
import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;
import at.kolleg.erplite.sharedkernel.events.OrderPaymentValidatedSpringEvent;
import at.kolleg.erplite.stockmanagement.business.DeliveryData;
import at.kolleg.erplite.stockmanagement.business.Packing;
import at.kolleg.erplite.stockmanagement.business.PackingItem;
import at.kolleg.erplite.stockmanagement.db.PackingRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
class StockIncomingMessageHandler implements ApplicationListener<OrderPaymentValidatedSpringEvent> {

    PackingRepository packingRepository;

    @Override
    @Async("threadPoolTaskExecutor")
    public void onApplicationEvent(OrderPaymentValidatedSpringEvent event) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order payment validated spring event ...");

        OrderResponse orderResponse = event.getOrderResponse();

        Packing packingToSaveToDb =
                Packing.builder()
                        .id(null)
                        .orderId(orderResponse.orderID())
                        .deliveryData(new DeliveryData(
                                        orderResponse.customerFirstname() + " " + orderResponse.customerLastname(),
                                        orderResponse.customerStreet(),
                                        orderResponse.customerZipcode(),
                                        orderResponse.customerCity(),
                                        orderResponse.customerCountry()
                                )
                        ).packingItemList(null) // List is generated down under
                        .build();

        List<PackingItem> packingItemList = new ArrayList<>();
        for (LineItemResponse lineItemResponse : orderResponse.orderLineItems()) {
            packingItemList.add(
                    new PackingItem(
                            null,
                            lineItemResponse.productNumber(),
                            lineItemResponse.productName(),
                            lineItemResponse.amount(),
                            false,
                            packingToSaveToDb
                    )
            );
        }
        packingToSaveToDb.setPackingItemList(packingItemList);
        this.packingRepository.save(packingToSaveToDb);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "New packing list created and saved in db ...");
    }
}
