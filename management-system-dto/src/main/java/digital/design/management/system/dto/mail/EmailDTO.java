package digital.design.management.system.dto.mail;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO implements Serializable {

    private String to;
    private String subject;//Тема сообщения
    private String templateLocation;//расположение шаблона
    private Map<String, Object> context;//динамические значения для thymeleaf
}