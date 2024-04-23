package at.kolleg.erplite;

import at.kolleg.erplite.sharedkernel.dtos.OrderResponse;
import at.kolleg.erplite.stockmanagement.business.Packing;
import at.kolleg.erplite.stockmanagement.business.PackingItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndTests {

    //https://springframework.guru/testing-spring-boot-restful-services/

    //https://stackoverflow.com/questions/52335562/integration-tests-on-spring-boot-throws-connection-refused


    @LocalServerPort
    private int port;
    private String baseUrl;
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        this.baseUrl = "http://localhost:" + port;
        this.restTemplate = new RestTemplate();
    }

    @Test
    public void placeAnOrder() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJSON = """
                        {
                          "customerID": "CUS1d34e56",
                          "customerFirstname": "Claudio",
                          "customerLastname": "Landerer",
                          "customerEmail": "a.b@c.de",
                          "customerStreet": "Landesrat-Gebhart-Straße 2",
                          "customerZipcode" : "6460",
                          "customerCity" : "Imst",
                          "customerCountry" : "Österreich",
                          "cartItems": [
                            {
                              "productNumber": "P123RE123D",
                              "productName" : "MacBook Pro 2022",
                              "priceNet" : 1000,
                              "tax" : 20,
                              "amount": 1
                            },
                            {
                              "productNumber": "O12345RE12",
                              "productName" : "Ipad Pro 2021",
                              "priceNet" : 99.99,
                              "tax" : 10,
                              "amount": 10
                            }
                          ]
                        }               
                """;

        HttpEntity<String> entity = new HttpEntity<String>(requestJSON, headers);
        ResponseEntity<OrderResponse> response = restTemplate.exchange(
                this.baseUrl + "/api/v1/orders/", HttpMethod.POST, entity,
                new ParameterizedTypeReference<OrderResponse>() {
                });
        OrderResponse result = response.getBody();
        Assertions.assertNotNull(result.orderID());
        Assertions.assertEquals("CUS1d34e56", result.customerID());
        Assertions.assertEquals(2, result.orderLineItems().size());
        System.out.println(result);
    }

    @Test
    public void placeOrderMakePaymentCheckGetOrderCheckOrderStatusCheckStock() {
        //Place an Order
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJSON = """
                        {
                          "customerID": "CUS1d34e56",
                          "customerFirstname": "Claudio",
                          "customerLastname": "Landerer",
                          "customerEmail": "a.b@c.de",
                          "customerStreet": "Landesrat-Gebhart-Straße 2",
                          "customerZipcode" : "6460",
                          "customerCity" : "Imst",
                          "customerCountry" : "Österreich",
                          "cartItems": [
                            {
                              "productNumber": "P123RE123D",
                              "productName" : "MacBook Pro 2022",
                              "priceNet" : 1000,
                              "tax" : 20,
                              "amount": 1
                            },
                            {
                              "productNumber": "O12345RE12",
                              "productName" : "Ipad Pro 2021",
                              "priceNet" : 99.99,
                              "tax" : 10,
                              "amount": 10
                            }
                          ]
                        }               
                """;

        HttpEntity<String> entity = new HttpEntity<String>(requestJSON, headers);
        ResponseEntity<OrderResponse> responsePlaceOrder = restTemplate.exchange(
                this.baseUrl + "/api/v1/orders/", HttpMethod.POST, entity,
                new ParameterizedTypeReference<OrderResponse>() {
                });

        String ORDERID = responsePlaceOrder.getBody().orderID();

        //Make payment check
        //POST http://localhost:8080/api/v1/orders/checkpayment/ONRc14721f
        ResponseEntity<String> checkPaymentResponse = restTemplate.exchange(
                this.baseUrl + "/api/v1/orders/checkpayment/" + ORDERID, HttpMethod.POST, null,
                new ParameterizedTypeReference<String>() {
                });


        //Get Order to check the status
        ResponseEntity<OrderResponse> responseGetStatusChangedOrder = restTemplate.exchange(
                this.baseUrl + "/api/v1/orders/" + ORDERID, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrderResponse>() {
                });

        Assertions.assertEquals("PAYMENT_VERIFIED", responseGetStatusChangedOrder.getBody().state());

        //Check if delivery info is created in stockservice
        ResponseEntity<Packing> responseStockPackingInserted = restTemplate.exchange(
                this.baseUrl + "/stock/packings/whithorderid/" + ORDERID, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertEquals(responsePlaceOrder.getBody().orderID(), responseStockPackingInserted.getBody().getOrderId());
        Assertions.assertEquals(2, responseStockPackingInserted.getBody().getPackingItemList().size());

        //Pack all the correspondig Items in orderservice
        //POST http://localhost:8080/stock/setPackedForPacking/16
        for (PackingItem packingItem : responseStockPackingInserted.getBody().getPackingItemList()) {
            restTemplate.exchange(
                    this.baseUrl + "/stock/setPackedForPacking/" + packingItem.getId(), HttpMethod.POST, null,
                    new ParameterizedTypeReference<>() {
                    });
        }

        //Because the calls between the customermanagement and the stockmanagement are async, we have to wait a little bit ...
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Get Order to check the status - should be Preparing_For_Delivery
        ResponseEntity<OrderResponse> responseGetStatusChangedTo = restTemplate.exchange(
                this.baseUrl + "/api/v1/orders/" + ORDERID, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrderResponse>() {
                });

        Assertions.assertEquals("PREPARING_FOR_DELIVERY", responseGetStatusChangedTo.getBody().state());
    }
}
