package digital.design.management.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "props.rabbitmq")
public class RabbitMQProperties {

    private String exchange;
    private String queue;
    private String routingkey;
}
