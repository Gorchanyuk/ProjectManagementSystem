package digital.design.management.system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig{

    @Value("${rabbitmq.queue.password}")
    private String queueNamePassword;
    @Value("${rabbitmq.queue.task}")
    private String queueNameTask;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey.password}")
    private String routingKeyPassword;
    @Value("${rabbitmq.routingkey.task}")
    private String routingKeyTask;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue queuePassword() {
        return new Queue(queueNamePassword, false);
    }

    @Bean
    public Queue queueTask() {
        return new Queue(queueNameTask, false);
    }

    @Bean
    public Binding bindingPassword(@Qualifier("queuePassword") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKeyPassword);
    }

    @Bean
    public Binding bindingTask(@Qualifier("queueTask") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKeyTask);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }
}