package at.kolleg.erplite.sharedkernel.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderPackedSpringEvent extends ApplicationEvent {

    private String orderID;

    public OrderPackedSpringEvent(Object source, String orderID) {
        super(source);
        this.orderID = orderID;
    }
}
