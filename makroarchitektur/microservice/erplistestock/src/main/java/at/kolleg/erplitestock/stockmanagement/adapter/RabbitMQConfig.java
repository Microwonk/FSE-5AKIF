package at.kolleg.erplitestock.stockmanagement.adapter;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //https://www.rabbitmq.com/tutorials/tutorial-one-spring-amqp.html
    //https://springhow.com/spring-boot-rabbitmq/

    @Bean
    public StockIncomingRabbitMessageRelay receiver() {
        return new StockIncomingRabbitMessageRelay();
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarables createPostRegistrationSchema(){
        return new Declarables(
                new FanoutExchange("x.erplitefanout"),//
               new TopicExchange("x.erplitetopic"),//
              //  new Queue("orderpackeddelivery" ),
             //  new Queue("orderpackedorder"),
                new Queue("q.order_paymentchecked"),//
              //  new Binding("orderpackeddelivery", Binding.DestinationType.QUEUE, "erplitefanoutexchange", "order-packed-delivery", null),
             //   new Binding("orderpackedorder", Binding.DestinationType.QUEUE, "erplitefanoutexchange", "order-packed-order", null),
                new Binding("q.order_paymentchecked", Binding.DestinationType.QUEUE,"x.erplitetopic","q.order_paymentchecked",null));
    }
}
