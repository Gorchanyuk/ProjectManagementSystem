package digital.design.management.system.app.config;

import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;
/*
При запуске программы проверяет, если уже в БД зарегестрированные пользователи,
если записей нет, то создает нового пользователя и записывает его в БД.
Это необходимо так как в системе может быть аутентифицирован только зарегестрированный пользователь
*/
@Configuration
@ConditionalOnWebApplication
@RequiredArgsConstructor
public class AdminInitConfig implements CommandLineRunner {

    private final AdminInitProperty property;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if(employeeRepository.findAll().isEmpty()) {
            Employee superuser = Employee.builder()
                    .uid(UUID.randomUUID())
                    .firstName(property.getUsername())
                    .lastName(property.getUsername())
                    .status(StatusEmployee.ACTIVE)
                    .username(property.getUsername())
                    .password(passwordEncoder.encode(property.getPassword()))
                    .build();

            employeeRepository.save(superuser);
        }
    }
}
