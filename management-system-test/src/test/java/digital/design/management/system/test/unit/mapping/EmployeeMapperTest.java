package digital.design.management.system.test.unit.mapping;

import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.mapping.impl.EmployeeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class EmployeeMapperTest {

    private static EmployeeMapper mapper;

    @BeforeAll
    public static void init(){
        mapper = new EmployeeMapper();
    }

    @Test
    public void shouldReturnNewEmployee(){
        Employee employee = mapper.dtoToEntity(getEmployeeDTO());

        Assertions.assertNotNull(employee.getLastName());
        Assertions.assertNotNull(employee.getFirstName());
        Assertions.assertNotNull(employee.getSurname());
        Assertions.assertNotNull(employee.getJobTitle());
        Assertions.assertNotNull(employee.getUsername());
        Assertions.assertNotNull(employee.getEmail());
    }

    @Test
    public void shouldReturnUpdatedEmployee(){
        Employee employee = getEmployee();
        Employee employeeUpdated = mapper.dtoToEntity(getEmployeeDTO(), getEmployee());

        Assertions.assertNotEquals(employee, employeeUpdated);
    }

    @Test
    public void shouldReturnEmployeeOutDto(){
        EmployeeOutDTO outDTO = mapper.entityToOutDto(getEmployee());

        Assertions.assertNotNull(outDTO.getLastName());
        Assertions.assertNotNull(outDTO.getFirstName());
        Assertions.assertNotNull(outDTO.getSurname());
        Assertions.assertNotNull(outDTO.getJobTitle());
        Assertions.assertNotNull(outDTO.getUsername());
        Assertions.assertNotNull(outDTO.getEmail());
        Assertions.assertNotNull(outDTO.getUid());
        Assertions.assertNotNull(outDTO.getStatus());
    }

    @Test
    public void shouldReturnEmployeeDto(){
        EmployeeDTO dto = mapper.entityToDto(getEmployee());

        Assertions.assertNotNull(dto.getLastName());
        Assertions.assertNotNull(dto.getFirstName());
        Assertions.assertNotNull(dto.getSurname());
        Assertions.assertNotNull(dto.getJobTitle());
        Assertions.assertNotNull(dto.getUsername());
        Assertions.assertNotNull(dto.getEmail());
    }

    private EmployeeDTO getEmployeeDTO(){
        return EmployeeDTO.builder()
                .lastName("someNewLastName")
                .firstName("someNewFirstName")
                .surname("someNewSurname")
                .jobTitle("someNewJobTitle")
                .username("someNewUsername")
                .email("someNewEmail")
                .build();
    }

    private Employee getEmployee(){
        return Employee.builder()
                .id(1L)
                .uid(UUID.randomUUID())
                .status(StatusEmployee.ACTIVE)
                .firstName("firstName")
                .lastName("lastName")
                .surname("surname")
                .jobTitle("jobTitle")
                .username("username")
                .email("email")
                .build();
    }
}
