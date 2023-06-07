package digital.design.management.system.rabbitmq;

import digital.design.management.system.dto.mail.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
    }

    @Profile("test")
    private void setEmailTo(EmailDTO dto) {
        dto.getContext().put("email", dto.getTo());
        dto.setTo(mailTo);
    }
}
