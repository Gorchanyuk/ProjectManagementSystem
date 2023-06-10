package digital.design.management.system.rabbitmq;

import digital.design.management.system.dto.mail.EmailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    @Value("${spring.mail.username}")
    private String mailTo;
    @Value("${rabbitmq.routingkey.password}")
    private String routingKeyPassword;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    private final RabbitTemplate rabbitTemplate;


    public void sendMessage(EmailDTO dto){

        setEmailTo(dto);
        rabbitTemplate.convertAndSend(exchange, routingKeyPassword, dto);
        log.info("Message to user: {} has been submitted to rabbitMQ", dto.getTo());
    }

    @Profile("develop")
    private void setEmailTo(EmailDTO dto) {
        dto.getContext().put("email", dto.getTo());
        dto.setTo(mailTo);
    }
}
