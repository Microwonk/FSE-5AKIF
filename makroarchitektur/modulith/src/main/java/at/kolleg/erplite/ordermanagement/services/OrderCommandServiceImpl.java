package at.kolleg.erplite.ordermanagement.services;

import at.kolleg.erplite.customermanagement.domain.CustomerID;
import at.kolleg.erplite.ordermanagement.domain.CustomerData;
import at.kolleg.erplite.ordermanagement.domain.LineItem;
import at.kolleg.erplite.ordermanagement.domain.Order;
import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPaymentValidatedEvent;
import at.kolleg.erplite.ordermanagement.domain.domainevents.OrderPlacedEvent;
import at.kolleg.erplite.ordermanagement.domain.valueobjects.*;
import at.kolleg.erplite.ordermanagement.ports.in.OrderCommandService;
import at.kolleg.erplite.ordermanagement.ports.out.OrderOutgoingMessageRelay;
import at.kolleg.erplite.ordermanagement.ports.out.OrderRepository;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderDataValidationException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderPaymentCheckFailedException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderPlacementNotSuccessfullException;
import at.kolleg.erplite.ordermanagement.services.exceptions.OrderStateChangeNotPossibleException;
import at.kolleg.erplite.ordermanagement.services.mapper.OrderResponseMapper;
import at.kolleg.erplite.sharedkernel.commands.OrderPaymentCheckCommand;
import at.kolleg.erplite.sharedkernel.commands.PlaceOrderCommand;
import at.kolleg.erplite.sharedkernel.dtos.CartItem;
import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
class OrderCommandServiceImpl implements OrderCommandService {

    private OrderRepository orderRepository;
    private OrderOutgoingMessageRelay orderOutgoingMessageRelay;

    //TODO: Transactions should be testet
    //TODO: It should be tested, if the publishing of the event leads to transactional-rollback ...

    @Transactional
    public OrderResponse handle(PlaceOrderCommand placeOrderCommand) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handle place order command ...");
        List<String> errors = validatePlaceOrderCommand(placeOrderCommand);
        if (errors.size() != 0) throw new OrderDataValidationException(errors);

        List<LineItem> lineItemList = new ArrayList<>();
        int i = 1;
        for (CartItem cartItem : placeOrderCommand.cartItems()) {
            lineItemList.add(new LineItem(
                            new OrderPosition(i),
                            new ProductNumber(cartItem.productNumber()),
                            new Name(cartItem.productName()),
                            new MonetaryAmount(new BigDecimal(cartItem.priceNet())),
                            new Percentage(cartItem.tax()),
                            new Amount(cartItem.amount())
                    )
            );
            i++;
        }

        Order orderToInsert = new Order(
                new OrderID("ONR" + UUID.randomUUID().toString().substring(0, 7)),
                new CustomerData(
                        new CustomerID(placeOrderCommand.customerID()),
                        new Name(placeOrderCommand.customerFirstname()),
                        new Name(placeOrderCommand.customerLastname()),
                        new Email(placeOrderCommand.customerEmail()),
                        placeOrderCommand.customerStreet(),
                        placeOrderCommand.customerZipcode(),
                        placeOrderCommand.customerCity(),
                        placeOrderCommand.customerCountry()
                ),
                LocalDateTime.now(),
                lineItemList,
                OrderState.PLACED
        );

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Insert new order in DB ...");
        Optional<Order> orderOptional = this.orderRepository.insert(orderToInsert);

        if (orderOptional.isPresent()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Publishing order placed domain event ...");
            orderOutgoingMessageRelay.publish(new OrderPlacedEvent(OrderResponseMapper.toResponseFromDomain(orderOptional.get())));
            return OrderResponseMapper.toResponseFromDomain(orderOptional.get());
        } else {
            throw new OrderPlacementNotSuccessfullException("OrderQueryServiceImpl: Order could not be placed!");
        }
    }

    @Override
    @Transactional
    public void handle(OrderPaymentCheckCommand orderPaymentCheckCommand) throws OrderPaymentCheckFailedException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling order payment check command ...");
        if (orderPaymentCheckCommand == null)
            throw new OrderPaymentCheckFailedException("Empty command for order payment check!");
        if (!OrderID.isValid(orderPaymentCheckCommand.orderID()))
            throw new OrderPaymentCheckFailedException("Order ID for order payment check not valid!");
        Optional<Order> optionalOrderToCheck = this.orderRepository.getById(new OrderID(orderPaymentCheckCommand.orderID()));
        if (optionalOrderToCheck.isPresent()) {
            Order order = optionalOrderToCheck.get();
            try {
                order.orderStateTransitionTo(OrderState.PAYMENT_VERIFIED);
                this.orderRepository.updateOrderWithNewState(order);
                this.orderOutgoingMessageRelay.publish(new OrderPaymentValidatedEvent(OrderResponseMapper.toResponseFromDomain(order)));
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Payment validated event published!");
            } catch (OrderStateChangeNotPossibleException orderStateChangeNotPossibleException) {
                throw new OrderPaymentCheckFailedException("Order payment check not possible. Order in wrong state! " + orderStateChangeNotPossibleException.getMessage());
            }
        } else {
            throw new OrderPaymentCheckFailedException("Order with Id " + orderPaymentCheckCommand.orderID() + " not found for payment check!");
        }
    }

    private List<String> validatePlaceOrderCommand(PlaceOrderCommand placeOrderCommand) {

        List<String> errors = new ArrayList<>();

        if (!CustomerID.isValid(placeOrderCommand.customerID())) errors.add("Invalid customer ID!");
        if (!Name.isValid(placeOrderCommand.customerFirstname())) errors.add("Invalid customer firstname!");
        if (!Name.isValid(placeOrderCommand.customerLastname())) errors.add("Invalid customer lastname!");
        if (!Name.isValid(placeOrderCommand.customerEmail())) errors.add("Invalid customer email!");

        if (placeOrderCommand.customerStreet() == null || placeOrderCommand.customerStreet().length() == 0)
            errors.add("Invalid customer street!");
        if (placeOrderCommand.customerCity() == null || placeOrderCommand.customerCity().length() == 0)
            errors.add("Invalid customer city!");
        if (placeOrderCommand.customerZipcode() == null || placeOrderCommand.customerZipcode().length() == 0)
            errors.add("Invalid customer zipcode!");
        if (placeOrderCommand.customerCountry() == null || placeOrderCommand.customerCountry().length() == 0)
            errors.add("Invalid customer country!");

        List<CartItem> cartItemList = placeOrderCommand.cartItems();

        if (cartItemList == null) {
            errors.add("Cart item list null!");
        } else if (cartItemList.size() == 0) {
            errors.add("Cart item list empty!");
        } else {
            for (CartItem cartItem : cartItemList) {
                if (!Name.isValid(cartItem.productName())) errors.add("Invalid product name!");
                if (!ProductNumber.isValid(cartItem.productNumber())) errors.add("Invalid product number!");
                if (!Amount.isValid(cartItem.amount())) errors.add("Invalid orderPosition for cart item!");
                if (!Percentage.isValid(cartItem.tax())) errors.add("Invalid tax for cart item!");
                if (!MonetaryAmount.isValid(new BigDecimal(cartItem.priceNet())))
                    errors.add("Invalid net price for cart item!");
            }
        }

        return errors;
    }
}
