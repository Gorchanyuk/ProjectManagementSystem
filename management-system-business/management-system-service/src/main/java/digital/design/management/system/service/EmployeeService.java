package digital.design.management.system.service;


import digital.design.management.system.common.exception.EditRemoteWorkerException;
import digital.design.management.system.common.exception.EntityDoesNotExistException;
import digital.design.management.system.common.exception.SuchUsernameAlreadyExistException;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.enumerate.StatusEmployee;
import digital.design.management.system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    public Employee findEmployeeByUid(UUID uid){

        return employeeRepository.findByUid(uid)
                .orElseThrow(EntityDoesNotExistException::new);
    }

    public List<EmployeeOutDTO> getEmployees() {
        List<Employee> employees =
                employeeRepository.findAllByStatus(StatusEmployee.ACTIV).stream()
                        .toList();

        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeOutDTO.class))
                .toList();
    }

    public EmployeeOutDTO getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EntityDoesNotExistException::new);

        return modelMapper.map(employee, EmployeeOutDTO.class);
    }

    public EmployeeOutDTO deleteEmployee(UUID uid) {
        Employee employee = employeeRepository.findByUid(uid)
                .orElseThrow(EntityDoesNotExistException::new);
        employee.setStatus(StatusEmployee.DELETED);
        employeeRepository.save(employee);

        return modelMapper.map(employee, EmployeeOutDTO.class);
    }

    public EmployeeOutDTO updateEmployee(UUID uid, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findByUid(uid)
                .orElseThrow(EntityDoesNotExistException::new);
        if (employee.getStatus().equals(StatusEmployee.DELETED))
            throw new EditRemoteWorkerException();
        if (employee.getUsername() != null && employeeDTO.getUsername() != null &&
                !employee.getUsername().equals(employeeDTO.getUsername())
                && employeeRepository.findByUsernameAndStatus(employeeDTO.getUsername(), StatusEmployee.ACTIV).isPresent()) {
            throw new SuchUsernameAlreadyExistException();
        }
        modelMapper.map(employeeDTO, employee);
        employeeRepository.save(employee);

        return modelMapper.map(employee, EmployeeOutDTO.class);
    }

    public List<EmployeeOutDTO> getEmployeeByKeyWord(String key) {
        List<Employee> employees =
                employeeRepository.findByKeyword(key, StatusEmployee.ACTIV);

        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeOutDTO.class))
                .toList();

    }

    public EmployeeOutDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setStatus(StatusEmployee.ACTIV);
        employeeRepository.save(employee);

        return modelMapper.map(employee, EmployeeOutDTO.class);
    }


}
