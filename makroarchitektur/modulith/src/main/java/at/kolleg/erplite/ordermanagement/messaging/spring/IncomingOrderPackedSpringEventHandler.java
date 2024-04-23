package at.kolleg.erplite.ordermanagement.messaging.spring;

import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPackedEvent;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderID;
import at.kolleg.erplite.ordermanagement.ports.in.OrderIncomingMessagesPort;
import at.kolleg.erplite.sharedkernel.events.OrderPackedSpringEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
class IncomingOrderPackedSpringEventHandler implements ApplicationListener<OrderPackedSpringEvent> {

    private OrderIncomingMessagesPort orderIncomingMessagesPort;

    @Override
    @Async("threadPoolTaskExecutor")
    public void onApplicationEvent(OrderPackedSpringEvent event) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order packed event received for order# " + event.getOrderID());
        this.orderIncomingMessagesPort.handle(new OrderPackedEvent(new OrderID(event.getOrderID())));
    }
}
