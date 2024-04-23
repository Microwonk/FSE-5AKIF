package at.itkolleg.delivery;

import at.itkolleg.delivery.sharedkernel.OrderDeliveredEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryRestController {

    private OrderDeliveryRepository orderDeliveryRepository;
    private RabbitTemplate template;

    @GetMapping()
    public ResponseEntity<List<OrderDelivery>> getAllDeliveries()
    {
        return ResponseEntity.ok(orderDeliveryRepository.findAll());
    }

    @PostMapping("/orderdelivered/{orderid}")
    public ResponseEntity orderDelivered(@PathVariable String orderid) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order delivered api request ...");
        Optional<OrderDelivery> orderDeliveryOptional = orderDeliveryRepository.findByOrderID(orderid);

        if (!orderDeliveryOptional.isPresent()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order with # " + orderid + " not present / not registered for delivery in delivery service!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order with # " + orderid + " not present / not registered for delivery in delivery service!"); //nothing to do --> idempotency
        } else {
            OrderDelivery orderDelivery = orderDeliveryOptional.get();

            if (orderDelivery.getDelivered()) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order with # " + orderDelivery.getOrderID() + " already delivered!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order already in dilvery state!"); //nothing to do --> idempotency
            } else {
                orderDelivery.setDelivered();
                orderDeliveryRepository.save(orderDelivery);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order delivered. Saved to DB. Publishing order delivered RabbitMQ event for order# " + orderDelivery.getOrderID());
                this.template.convertAndSend("q.order_delivered", new OrderDeliveredEvent(orderDelivery.getOrderID()));
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Order delivered RabbitMQ event for order# " + orderDelivery.getOrderID() + " published!");
                return ResponseEntity.status(HttpStatus.OK).body("Order with id " +orderDelivery.getOrderID() + " delivered!");
            }
        }
    }
}
