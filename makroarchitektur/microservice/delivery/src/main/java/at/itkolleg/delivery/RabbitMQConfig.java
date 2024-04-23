package at.itkolleg.delivery;

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
    public DeliveryIncomingMessageRelay receiver() {
        return new DeliveryIncomingMessageRelay();
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

   @Bean
    public Declarables createPostRegistrationSchema(){
        return new Declarables(
                new FanoutExchange("x.erplitefanout"),
                new TopicExchange("x.erplitetopic"),
                new Queue("q.orderpacked1"),
                new Queue("q.initiate_delivery"),
                new Queue("q.order_delivered"),
                new Queue("q.order_in_delivery"),
                new Binding("q.orderpacked1", Binding.DestinationType.QUEUE, "x.erplitefanout", "q.orderpacked1", null),
                new Binding("q.initiate_delivery", Binding.DestinationType.QUEUE, "x.erplitetopic", "q.initiate_delivery", null),
                new Binding("q.order_in_delivery", Binding.DestinationType.QUEUE, "x.erplitetopic", "q.order_in_delivery", null),
                new Binding("q.order_delivered", Binding.DestinationType.QUEUE, "x.erplitetopic", "q.order_delivered", null));
    }
}
