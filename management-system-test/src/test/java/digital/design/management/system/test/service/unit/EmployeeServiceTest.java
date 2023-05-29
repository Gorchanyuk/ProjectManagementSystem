package digital.design.management.system.test.service.unit;

import digital.design.management.system.common.exception.EmployeeDoesNotExistException;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.EmployeeRepository;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(locations="classpath:application-test.properties")
public class EmployeeServiceTest {

    @Spy
    private static EmployeeRepository employeeRepository;
    @Spy
    private static Mapper<Employee, EmployeeDTO, EmployeeOutDTO> mapper;
    @Spy
    private static ProjectTeamRepository projectTeamRepository;
    @Spy
    private static PasswordEncoder passwordEncoder;
    @InjectMocks
    private static EmployeeService employeeService;


    @Test
    public void userNotFound() {
        UUID uid = UUID.randomUUID();
        Mockito.when(employeeRepository.findByUidAndStatus(any(), any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EmployeeDoesNotExistException.class, () -> employeeService.getEmployeeByUid(uid));
    }
}