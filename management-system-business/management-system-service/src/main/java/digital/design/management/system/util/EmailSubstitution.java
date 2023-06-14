package digital.design.management.system.util;

import digital.design.management.system.dto.mail.EmailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("develop")
public class EmailSubstitution {

    @Value("${spring.mail.username}")
    private String mailTo;

    public void setEmailTo(EmailDTO dto) {
        dto.getContext().put("email", dto.getTo());
        dto.setTo(mailTo);
    }
}
