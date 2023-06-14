package digital.design.management.system.test.unit.service;

import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.common.exception.EmployeeDoesNotExistException;
import digital.design.management.system.common.exception.SuchUsernameAlreadyExistException;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.mapping.impl.EmployeeMapper;
import digital.design.management.system.rabbitmq.MessageProducer;
import digital.design.management.system.repository.EmployeeRepository;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.service.impl.EmployeeServiceImpl;
import digital.design.management.system.util.CreatorMailDTO;
import digital.design.management.system.util.PasswordGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Spy
    private EmployeeMapper mapper;
    @Mock
    private ProjectTeamRepository projectTeamRepository;
    @Mock
    private MessageProducer messageProducer;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PasswordGenerator passwordGenerator;
    @Spy
    private CreatorMailDTO creatorMailDTO;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void findByUidShouldThrowEmployeeDoesNotExistException() {
        UUID uid = UUID.randomUUID();
        when(employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIVE)).thenReturn(Optional.empty());

        Assertions.assertThrows(EmployeeDoesNotExistException.class, () -> employeeService.getEmployeeByUid(uid));
    }

    @Test
    public void findByUidShouldReturnEmployee() {
        UUID uid = UUID.randomUUID();
        when(employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIVE)).thenReturn(Optional.of(new Employee()));

        Assertions.assertNotNull(employeeService.findByUid(uid));
    }

    @Test
    public void getEmployeesShouldReturnListEmployees() {
        when(employeeRepository.findTop100ByStatus(any())).thenReturn(List.of(new Employee()));

        Assertions.assertFalse(employeeService.getEmployees().isEmpty());
    }

    @Test
    public void getEmployeeByUidShouldReturnEmployeeOutDto() {
        UUID uid = UUID.randomUUID();
        when(employeeRepository.findByUidAndStatus(any(), any())).thenReturn(Optional.of(new Employee()));

        Assertions.assertNotNull(employeeService.getEmployeeByUid(uid));
    }

    @Test
    public void deleteEmployeeShouldChangeStatusOnDelete() {
        UUID uid = UUID.randomUUID();
        when(employeeRepository.findByUidAndStatus(any(), any())).thenReturn(Optional.of(new Employee()));

        EmployeeOutDTO outDTOUpdate = employeeService.deleteEmployee(uid);

        verify(projectTeamRepository).deleteAllByEmployeeId(any());
        Assertions.assertEquals(StatusEmployee.DELETED, outDTOUpdate.getStatus());
    }

    @Test
    public void updateEmployeeShouldThrowSuchUsernameAlreadyExistException() {
        UUID uid = UUID.randomUUID();
        Employee employee = Employee.builder().username("username").build();
        when(employeeRepository.findByUidAndStatus(any(), any())).thenReturn(Optional.of(employee));
        when(employeeRepository.findByUsernameAndStatus(anyString(), any())).thenReturn(Optional.of(new Employee()));
        EmployeeDTO dto = EmployeeDTO.builder().username("newUsername").build();

        Assertions.assertThrows(SuchUsernameAlreadyExistException.class,
                () -> employeeService.updateEmployee(uid, dto));
    }

    @Test
    public void updateEmployeeShouldUpdateEmployee() {
        UUID uid = UUID.randomUUID();
        Employee employee = Employee.builder().build();
        when(employeeRepository.findByUidAndStatus(any(), any())).thenReturn(Optional.of(employee));
        EmployeeDTO dto = EmployeeDTO.builder()
                .email("email")
                .surname("newSurname")
                .build();

        EmployeeOutDTO outDTO = employeeService.updateEmployee(uid, dto);

        verify(employeeRepository).save(employee);
        verify(passwordGenerator).generate();
        verify(passwordEncoder).encode(any());
        Assertions.assertNotNull(outDTO);
        Assertions.assertEquals(dto.getUsername(), outDTO.getUsername());
        Assertions.assertEquals(dto.getSurname(), outDTO.getSurname());
    }

    @Test
    public void getEmployeeByKeyWordShouldFindEmloyee() {
        when(employeeRepository.findByKeyword(anyString(), any())).thenReturn(List.of(new Employee()));

        Assertions.assertFalse(employeeService.getEmployeeByKeyWord("key").isEmpty());
    }

    @Test
    public void createEmployeeShouldCreateNewEmployee() {
        EmployeeDTO dto = EmployeeDTO.builder().email("email").build();

        EmployeeOutDTO outDTO = employeeService.createEmployee(dto);

        verify(employeeRepository).save(any());
        verify(passwordGenerator).generate();
        verify(passwordEncoder).encode(any());
        Assertions.assertNotNull(outDTO);

    }


}