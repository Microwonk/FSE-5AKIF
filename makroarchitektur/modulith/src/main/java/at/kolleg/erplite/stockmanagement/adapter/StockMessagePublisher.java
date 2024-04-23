package at.kolleg.erplite.stockmanagement.adapter;

import at.kolleg.erplite.sharedkernel.events.OrderPackedSpringEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
class StockMessagePublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public void publishOrderPackedSpringEventForOrderId(String orderID) {
        OrderPackedSpringEvent orderPackedSpringEvent = new OrderPackedSpringEvent(this, orderID);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Publishing order fully packed event ...");
        applicationEventPublisher.publishEvent(orderPackedSpringEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order fully packed event published!");
    }

}
