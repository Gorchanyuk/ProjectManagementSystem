package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.mapping.Mapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class EmployeeMapper implements Mapper<Employee, EmployeeDTO, EmployeeOutDTO> {

    @Override
    public Employee dtoToEntity(EmployeeDTO dto) {
        log.debug("Mapping EmployeeDTO to Employee");
        return Employee.builder()
                .uid(UUID.randomUUID())
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .surname(dto.getSurname())
                .jobTitle(dto.getJobTitle())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }

    @Override
    public Employee dtoToEntity(EmployeeDTO dto, Employee employee) {
        log.debug("Mapping an EmployeeDTO to an Employee in a given employee");
        employee.setLastName(dto.getLastName());
        employee.setFirstName(dto.getFirstName());
        employee.setSurname(dto.getSurname());
        employee.setJobTitle(dto.getJobTitle());
        employee.setUsername(dto.getUsername());
        employee.setEmail(dto.getEmail());

        return employee;
    }

    @Override
    public EmployeeOutDTO entityToOutDto(Employee employee) {
        log.debug("Mapping Employee to EmployeeOutDTO");
        return EmployeeOutDTO.builder()
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .surname(employee.getSurname())
                .jobTitle(employee.getJobTitle())
                .username(employee.getUsername())
                .email(employee.getEmail())
                .uid(employee.getUid())
                .status(employee.getStatus())
                .build();
    }

    @Override
    public EmployeeDTO entityToDto(Employee employee){
        log.debug("Mapping Employee to EmployeeDTO");
        return EmployeeDTO.builder()
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .surname(employee.getSurname())
                .jobTitle(employee.getJobTitle())
                .username(employee.getUsername())
                .email(employee.getEmail())
                .build();
    }
}
