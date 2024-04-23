package at.kolleg.erplite.sharedkernel.events;

import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderPaymentValidatedSpringEvent extends ApplicationEvent {

    private OrderResponse orderResponse;

    public OrderPaymentValidatedSpringEvent(Object source, OrderResponse orderResponse) {
        super(source);
        this.orderResponse = orderResponse;
    }
}
