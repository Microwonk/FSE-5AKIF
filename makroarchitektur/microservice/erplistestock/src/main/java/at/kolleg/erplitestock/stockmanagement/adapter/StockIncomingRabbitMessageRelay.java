package at.kolleg.erplitestock.stockmanagement.adapter;

import at.kolleg.erplitestock.stockmanagement.business.DeliveryData;
import at.kolleg.erplitestock.stockmanagement.business.Packing;
import at.kolleg.erplitestock.stockmanagement.business.PackingItem;
import at.kolleg.erplitestock.stockmanagement.db.PackingRepository;
import at.kolleg.erplitestock.sharedkernel.LineItemResponse;
import at.kolleg.erplitestock.sharedkernel.OrderPaymentValidatedEvent;
import at.kolleg.erplitestock.sharedkernel.OrderResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockIncomingRabbitMessageRelay {

    @Autowired
    private PackingRepository packingRepository;

    @RabbitListener(queues = "q.order_paymentchecked")
    public void receive(OrderPaymentValidatedEvent event)
    {
        if(packingRepository.findByOrderId(event.orderResponse().orderID()).isPresent()) return; //Guarantee for idempotency (packing for certain orderId already in DB)

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order payment validated Rabbit MQ event ...");

        OrderResponse orderResponse = event.orderResponse();

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
