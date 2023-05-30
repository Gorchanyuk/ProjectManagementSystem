package digital.design.management.system.test.service.integrations.repository;

import digital.design.management.system.entity.Employee;
import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.repository.EmployeeRepository;
import digital.design.management.system.test.service.integrations.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;


@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(locations="classpath:application-test.properties")
public class EmployeeRepositoryContainerIT extends BaseTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Value("${spring.datasource.url}")
    private String url;

    @Test
    public void checkDatasourceUrl() {
        Assertions.assertNotEquals("jdbc:postgresql://localhost/postgres", url);
    }

    @Test
    public void mustFindEmployeeByUsername() {
        final UUID uid = UUID.randomUUID();
        Employee employee = Employee.builder()
                .uid(uid)
                .firstName("name")
                .lastName("lname")
                .username("uname")
                .status(StatusEmployee.ACTIVE)
                .build();
        employee = employeeRepository.save(employee);
        Optional<Employee> optionalEmployee = employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIVE);
        Assertions.assertTrue(optionalEmployee.isPresent());
        Assertions.assertEquals(employee, optionalEmployee.get());
    }

}
