package at.kolleg.erplitestock.stockmanagement.adapter;

import at.kolleg.erplitestock.sharedkernel.OrderPackedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
class StockMessagePublisher {

    @Autowired
    private RabbitTemplate template;

    public void publishOrderPackedEventForOrderId(String orderID) {
        OrderPackedEvent orderPackedEvent = new OrderPackedEvent(orderID);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Publishing order fully packed event ...");
        this.template.convertAndSend("x.erplitefanout","", orderPackedEvent);
        System.out.println("LOG: " +  orderPackedEvent);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order fully packed event published!");
    }

}
