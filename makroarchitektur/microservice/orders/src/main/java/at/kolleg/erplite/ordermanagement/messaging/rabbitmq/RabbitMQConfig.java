package at.kolleg.erplite.ordermanagement.messaging.rabbitmq;

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
    public Declarables createPostRegistrationSchema() {
        return new Declarables(
               new FanoutExchange("x.erplitefanout"),
                new TopicExchange("x.erplitetopic"),
               new Queue("q.orderpacked2"),
                new Queue("q.order_paymentchecked"),
                new Queue("q.order_placed"),
                new Queue("q.initiate_delivery"),
                new Queue("q.order_in_delivery"),
                new Queue("q.order_delivered"),
                new Binding("q.order_paymentchecked", Binding.DestinationType.QUEUE, "x.erplitetopic", "q.order_paymentchecked", null),
                new Binding("q.order_placed", Binding.DestinationType.QUEUE, "x.erplitetopic", "q.order_placed", null),
                new Binding("q.initiate_delivery", Binding.DestinationType.QUEUE, "x.erplitetopic", "q.initiate_delivery", null),
                new Binding("q.order_in_delivery", Binding.DestinationType.QUEUE, "x.erplitetopic", "q.order_in_delivery", null),
                new Binding("q.orderpacked2", Binding.DestinationType.QUEUE, "x.erplitefanout", "q.orderpacked2", null),
                new Binding("q.order_delivered", Binding.DestinationType.QUEUE, "x.erplitetopic", "q.order_delivered", null));
    }

    @Bean
    public OrderIncomingRabbitMessageRelay receiver() {
        return new OrderIncomingRabbitMessageRelay();
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
