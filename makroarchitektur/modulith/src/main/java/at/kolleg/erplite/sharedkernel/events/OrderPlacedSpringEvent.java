package at.kolleg.erplite.sharedkernel.events;

import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

//TODO: check sharedKernel classes -> keep it small
@Getter
public class OrderPlacedSpringEvent extends ApplicationEvent {

    private OrderResponse orderData;

    public OrderPlacedSpringEvent(Object source, OrderResponse orderData) {
        super(source);
        this.orderData = orderData;
    }
}
