package digital.design.management.system.service;

import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    Employee findByUid(UUID uid);

    List<EmployeeOutDTO> getEmployees();

    EmployeeOutDTO getEmployeeByUid(UUID uid);

    EmployeeOutDTO deleteEmployee(UUID uid);

    EmployeeOutDTO updateEmployee(UUID uid, EmployeeDTO employeeDTO);

    List<EmployeeOutDTO> getEmployeeByKeyWord(String key);

    EmployeeOutDTO createEmployee(EmployeeDTO employeeDTO);
}
