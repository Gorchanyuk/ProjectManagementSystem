package digital.design.management.system.test.service.integrations.service;

import digital.design.management.system.app.ManagementSystem;
import digital.design.management.system.common.exception.EmployeeDoesNotExistException;
import digital.design.management.system.common.exception.SuchUsernameAlreadyExistException;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.EmployeeRepository;
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.test.service.integrations.BaseTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringJUnitConfig(ManagementSystem.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
public class EmployeeServiceContainersIT extends BaseTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    Mapper<Employee, EmployeeDTO, EmployeeOutDTO> mapper;

    @Autowired
    EntityManager entityManager;

    @Test
    public void shouldGetListEmployee() {

        List<EmployeeOutDTO> employees = employeeService.getEmployees();
        Assertions.assertFalse(employees.isEmpty());
    }

    @RepeatedTest(10)
    public void shouldThrowEmployeeDoesNotExistException() {
        UUID uid = UUID.randomUUID();

        Assertions.assertThrows(EmployeeDoesNotExistException.class, () -> employeeService.getEmployeeByUid(uid),
                "Произошел сбой теста при значении uid =" + uid);
    }

    @Test
    public void shouldGetEmployeeByUid() {
        Employee employee = employeeRepository.findTop100ByStatus(StatusEmployee.ACTIVE).stream().findAny().get();
        EmployeeOutDTO dto = employeeService.getEmployeeByUid(employee.getUid());

        Assertions.assertFalse(ObjectUtils.isEmpty(dto));
        Assertions.assertEquals(employee.getUid(), employeeService.getEmployeeByUid(employee.getUid()).getUid());
    }

    @Test
    public void shouldDeleteEmployeeByUid() {
        Employee employee = employeeRepository.findTop100ByStatus(StatusEmployee.ACTIVE).stream().findAny().get();
        assert employee.getStatus().equals(StatusEmployee.ACTIVE);
        EmployeeOutDTO dto = employeeService.deleteEmployee(employee.getUid());
        Optional<Employee> optionalEmployee = employeeRepository.findByUidAndStatus(employee.getUid(), StatusEmployee.ACTIVE);

        Assertions.assertEquals(dto.getStatus(), StatusEmployee.DELETED);
        Assertions.assertTrue(optionalEmployee.isEmpty());
    }

    @Test
    public void shouldSuchUsernameAlreadyExistException() {
        List<Employee> employees = employeeRepository.findTop100ByStatus(StatusEmployee.ACTIVE);
        assert employees.size() > 1;
        Employee employee1 = employees.get(0);
        Employee employee2 = employees.get(1);
        String username = "uniqueUsername";
        employee1.setUsername(username);
        employeeRepository.save(employee1);
        EmployeeDTO dto = mapper.entityToDto(employee2);
        dto.setUsername(username);
        UUID uid = employee2.getUid();

        Assertions.assertThrows(SuchUsernameAlreadyExistException.class,
                () -> employeeService.updateEmployee(uid, dto));
    }

    @Test
    public void shouldUpdateEmployee() {
        Employee employeeStart = addEmployee();

        EmployeeDTO dto = mapper.entityToDto(employeeStart);
        String username = UUID.randomUUID().toString();
        String firstName = "someNewName";
        String email = "prog_ramm_er@internet.ru";
        dto.setFirstName(firstName);
        dto.setUsername(username);
        dto.setEmail(email);
        UUID uid = employeeStart.getUid();

        employeeService.updateEmployee(uid, dto);
        Employee employeeEnd = employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIVE).get();

        Assertions.assertNotEquals(employeeStart, employeeEnd);
        Assertions.assertFalse(ObjectUtils.isEmpty(employeeEnd.getEmail()));
    }

    @Test
    public void shouldFindAllEmployeeIfKeyWordNull(){
        List<EmployeeOutDTO> employeeByKey = employeeService.getEmployeeByKeyWord("");
        List<EmployeeOutDTO> employeeAll = employeeService.getEmployees();

        Assertions.assertEquals(employeeAll.size(), employeeByKey.size());
    }

    @Test
    public void shouldFindEmployeeByKeyWord(){
        Employee employee1 = addEmployee();
        employee1.setLastName("some very complicated long lastname");

        Employee employee2 = addEmployee();
        employee2.setSurname("some very complicated long surname");

        Employee employee3 = addEmployee();
        employee3.setFirstName("some very complicated long firstname");

        Employee employee4 = addEmployee();
        employee4.setEmail("some.very.complicated.long@email.com");

        Employee employee5 = addEmployee();
        employee5.setUsername("someVeryComplicatedLongUsername");

        employeeRepository.saveAll(List.of(employee1, employee2, employee3, employee4, employee5));
        List<EmployeeOutDTO> employees = employeeService.getEmployeeByKeyWord("complic");

        assert employeeRepository.findTop100ByStatus(StatusEmployee.ACTIVE).size() > employees.size();
        Assertions.assertEquals(5, employees.size(), 1);
    }

    @Test
    public void shouldCreateNewEmployee(){
        EmployeeDTO dto = EmployeeDTO.builder()
                .firstName("fname")
                .lastName("lname")
                .build();

        EmployeeOutDTO outDTO = employeeService.createEmployee(dto);

        Assertions.assertEquals(outDTO.getStatus(), StatusEmployee.ACTIVE);
        Assertions.assertFalse(ObjectUtils.isEmpty(outDTO.getUid()));
    }



    private Employee addEmployee(){
        Employee employee = Employee.builder()
                .uid(UUID.randomUUID())
                .firstName("fname")
                .lastName("lname")
                .status(StatusEmployee.ACTIVE)
                .build();
        employee = employeeRepository.save(employee);
        entityManager.detach(employee);
        return employee;
    }
}
