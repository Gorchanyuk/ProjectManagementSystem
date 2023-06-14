package digital.design.management.system.rabbitmq;

import digital.design.management.system.config.RabbitMQProperties;
import digital.design.management.system.dto.mail.EmailDTO;
import digital.design.management.system.util.EmailSubstitution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;
    private final EmailSubstitution emailSubstitution;


    public void sendMessage(EmailDTO dto){

        emailSubstitution.setEmailTo(dto);
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchange(), rabbitMQProperties.getRoutingkey(), dto);
        log.info("Message to user: {} has been submitted to rabbitMQ", dto.getTo());
    }

}
