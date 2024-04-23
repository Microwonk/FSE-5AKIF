package at.kolleg.erplite.ordermanagement.api;

import at.kolleg.erplite.ordermanagement.ports.in.OrderCommandService;
import at.kolleg.erplite.ordermanagement.ports.in.OrderQueryService;
import at.kolleg.erplite.sharedkernel.commands.OrderPaymentCheckCommand;
import at.kolleg.erplite.sharedkernel.commands.PlaceOrderCommand;
import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;
import at.kolleg.erplite.sharedkernel.queries.GetAllOrdersSortedAndPagedQuery;
import at.kolleg.erplite.sharedkernel.queries.GetOrderByIdQuery;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
class OrderRestController {

    private OrderCommandService orderCommandService;
    private OrderQueryService orderQueryService;

    @PostMapping("/orders/")
    public ResponseEntity placeNewOrder(@RequestBody @Valid PlaceOrderCommand placeOrderCommand, BindingResult bindingResult) {

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling place new order api request ...");

        HashMap<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Errors in placeOrderCommand detected!");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            throw new OrderPlacedFieldValidationException("Validation errors for order placement!", errors);
        }

        OrderResponse orderResponse = orderCommandService.handle(placeOrderCommand);

        String resourceLocation = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/orders/" + orderResponse.orderID();
        try {
            return ResponseEntity.created(new URI(resourceLocation)).body(orderResponse);
        } catch (URISyntaxException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/orders/")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling get all orders api request ...");
        return ResponseEntity.ok().body(this.orderQueryService.getAllOrders());
    }

    @GetMapping("/orders/sortedandpaged/")
    public ResponseEntity<List<OrderResponse>> getAllOrdersSortedAndPaged(

            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pagesize,
            @RequestParam Optional<String> sortedby) {

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling getAllOrdersSortedAndPaged api request ...");

        if (page.isPresent() && pagesize.isPresent() && sortedby.isPresent()) {
            return ResponseEntity.ok().body(this.orderQueryService.handle(new GetAllOrdersSortedAndPagedQuery(page.get(), pagesize.get(), sortedby.get())));
        } else {
            return ResponseEntity.ok().body(this.orderQueryService.handle(new GetAllOrdersSortedAndPagedQuery(0, 10, "orderID")));
        }
    }

    @GetMapping("/orders/{orderid}")
    public ResponseEntity<OrderResponse> getOrderWithId(@PathVariable String orderid) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling get order by id api request ...");
        return ResponseEntity.ok().body(this.orderQueryService.handle(new GetOrderByIdQuery(orderid)));
    }

    @PostMapping("/orders/checkpayment/{orderid}")
    public ResponseEntity validatePaymentForOrderWithId(@PathVariable String orderid) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling check payment for order api request ...");
        this.orderCommandService.handle(new OrderPaymentCheckCommand(orderid));
        return ResponseEntity.accepted().body("Order payment check executed. Order payment ok!");
    }
}
