package digital.design.management.system.util;

import digital.design.management.system.dto.mail.EmailDTO;
import digital.design.management.system.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CreatorMailDTO {

    public EmailDTO getDtoForPassword(Employee employee, String password){
        Map<String, Object> context = getContext(employee);
        context.put("password", password);
        return EmailDTO.builder()
                .subject("Регистрация в Management system")
                .templateLocation("email.password")
                .to(employee.getEmail())
                .context(context)
                .build();
    }

    public EmailDTO getDtoForTask(Employee employee, String task, String project){
        Map<String, Object> context = getContext(employee);
        context.put("task", task);
        context.put("project", project);
        return EmailDTO.builder()
                .subject("Новая задача")
                .templateLocation("email.task")
                .to(employee.getEmail())
                .context(context)
                .build();
    }

    private Map<String, Object> getContext(Employee employee){
        Map<String, Object> context = new HashMap<>();
        context.put("firstName", employee.getFirstName());
        context.put("lastName", employee.getLastName());
        context.put("username", employee.getUsername());
        return context;
    }
}
