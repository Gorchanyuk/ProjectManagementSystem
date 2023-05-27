package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.mapping.Mapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmployeeMapper implements Mapper<Employee, EmployeeDTO, EmployeeOutDTO> {

    @Override
    public Employee dtoToEntity(EmployeeDTO dto){

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

        employee.setLastName(dto.getLastName());
        employee.setFirstName(dto.getFirstName());
        employee.setSurname(dto.getSurname());
        employee.setJobTitle(dto.getJobTitle());
        employee.setUsername(dto.getUsername());
        employee.setEmail(dto.getEmail());

        return employee;
    }

    @Override
    public EmployeeOutDTO entityToOutDto(Employee employee){

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
}
