package digital.design.management.system.app.config;

import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
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
public class AdminInitConfig implements CommandLineRunner {

    @Value("${init.admin.username}")
    private String username;

    @Value("${init.admin.password}")
    private String password;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitConfig(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if(employeeRepository.findAll().isEmpty()) {
            Employee superuser = Employee.builder()
                    .uid(UUID.randomUUID())
                    .firstName(username)
                    .lastName(username)
                    .status(StatusEmployee.ACTIVE)
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .build();

            employeeRepository.save(superuser);
        }
    }
}
