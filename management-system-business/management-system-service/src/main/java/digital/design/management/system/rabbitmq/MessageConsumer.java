package digital.design.management.system.rabbitmq;

import digital.design.management.system.dto.mail.EmailDTO;
import digital.design.management.system.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageConsumer {

    private final EmailService emailService;
    private final MessageConverter messageConverter;

    @RabbitListener(queues = "${rabbitmq.queue.password}")
    public void receiveMessagePassword(Message message){
        EmailDTO dto = (EmailDTO) messageConverter.fromMessage(message);
        emailService.sendEmail(dto);
    }
}
