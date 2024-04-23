package at.kolleg.erplite.ordermanagement.services;


import at.kolleg.erplite.ordermanagement.ports.in.OrderCommandService;
import at.kolleg.erplite.ordermanagement.ports.out.OrderOutgoingMessageRelay;
import at.kolleg.erplite.ordermanagement.ports.out.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderCommandServiceTests {

    private OrderCommandService orderCommandService;

    @BeforeEach
    public void setup(@Mock OrderRepository orderRepository, @Mock OrderOutgoingMessageRelay orderOutgoingMessageRelay) {
        this.orderCommandService = new OrderCommandServiceImpl(orderRepository, orderOutgoingMessageRelay);


    }

    @Test
    public void testPlaceOrderCommandProcessing() {
        //Command Objekt definieren

        //Insert auf Repo und Message delivery mocken

        //Abfeuern
        
        //Mockito.lenient().when(orderRepository.insert()).thenReturn(10);
        //https://www.baeldung.com/mockito-junit-5-extension
    }
}
