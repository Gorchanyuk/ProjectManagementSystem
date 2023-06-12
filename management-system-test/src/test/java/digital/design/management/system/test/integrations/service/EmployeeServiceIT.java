package digital.design.management.system.test.integrations.service;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.EmployeeRepository;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.test.integrations.BaseTest;
import digital.design.management.system.test.util.GenerateEmployee;
import digital.design.management.system.test.util.GenerateProject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(locations="classpath:application-test.properties")
public class EmployeeServiceIT extends BaseTest {

    @Autowired
    private GenerateEmployee generateEmployee;
    @Autowired
    private GenerateProject generateProject;
    @Autowired
    ProjectTeamRepository projectTeamRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private Mapper<Employee, EmployeeDTO, EmployeeOutDTO> mapper;

    @Test
    public void getEmployeesShouldGetListEmployee() {
        int count = 10;
        generateEmployee.addEmployees(count);
        List<EmployeeOutDTO> employees = employeeService.getEmployees();

        Assertions.assertEquals(count + 1, employees.size());// +1 потому что при пустой БД создается суперпользователь
    }

    @Test
    public void getEmployeeByUidShouldGetEmployeeByUid() {
        List<Employee> employees = generateEmployee.addEmployees(10);
        UUID uid = employees.get(0).getUid();
        EmployeeOutDTO dto = employeeService.getEmployeeByUid(uid);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(uid, dto.getUid());
    }

    @Test
    public void deleteEmployeeShouldChangeStatusEmployee() {
        UUID uid = generateEmployee.addEmployees(10).get(0).getUid();
        EmployeeOutDTO dto = employeeService.deleteEmployee(uid);
        Optional<Employee> optionalEmployee = employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIVE);

        Assertions.assertEquals(dto.getStatus(), StatusEmployee.DELETED);
        Assertions.assertTrue(optionalEmployee.isEmpty());
    }

    @Test
    public void deleteEmployeeShouldDeleteEmployeeFromTeams(){
        Project project = generateProject.addProjects(1).get(0);
        Employee employee = generateEmployee.addEmployees(1).get(0);
        ProjectTeam projectTeam = new ProjectTeam(project, employee, RoleEmployee.ANALYST);
        projectTeamRepository.save(projectTeam);

        employeeService.deleteEmployee(employee.getUid());

        Assertions.assertTrue(projectTeamRepository.findAll().isEmpty());
    }

    @Test
    public void updateEmployeeShouldUpdateEmployee() {
        Employee employee = generateEmployee.addEmployees(1).get(0);
        EmployeeOutDTO outDto = mapper.entityToOutDto(employee);

        EmployeeDTO dto = mapper.entityToDto(employee);
        String username = "username";
        String firstName = "someNewName";
        String email = "prog_ramm_er@internet.ru";
        dto.setFirstName(firstName);
        dto.setUsername(username);
        dto.setEmail(email);
        UUID uid = employee.getUid();

        employeeService.updateEmployee(uid, dto);
        Employee employeeUpdate = employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIVE).get();
        EmployeeOutDTO outDtoUpdate = mapper.entityToOutDto(employeeUpdate);

        Assertions.assertNotEquals(outDto, outDtoUpdate);
    }

    @Test
    public void getEmployeeByKeyWordShouldFindEmployee() {
        List<Employee> employees = generateEmployee.addEmployees(10);
        Employee employee1 = employees.get(0);
        employee1.setLastName("some very complicated long lastname");

        Employee employee2 = employees.get(1);
        employee2.setSurname("some very complicated long surname");

        Employee employee3 = employees.get(2);
        employee3.setFirstName("some very complicated long firstname");

        Employee employee4 = employees.get(3);
        employee4.setEmail("some.very.complicated.long@email.com");

        Employee employee5 = employees.get(4);
        employee5.setUsername("someVeryComplicatedLongUsername");

        employeeRepository.saveAll(List.of(employee1, employee2, employee3, employee4, employee5));
        List<EmployeeOutDTO> findEmployees = employeeService.getEmployeeByKeyWord("complic");

        Assertions.assertEquals(5, findEmployees.size());
    }

    @Test
    public void createEmployeeShouldCreateNewEmployee() {
        EmployeeDTO dto = EmployeeDTO.builder()
                .firstName("fname")
                .lastName("lname")
                .email("prog_ramm_er@internet.ru")
                .build();

        EmployeeOutDTO outDTO = employeeService.createEmployee(dto);
        Optional<Employee> employee = employeeRepository.findByUidAndStatus(outDTO.getUid(), StatusEmployee.ACTIVE);

        Assertions.assertTrue(employee.isPresent());
    }
}
