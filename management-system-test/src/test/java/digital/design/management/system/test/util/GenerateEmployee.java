package digital.design.management.system.test.util;

import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class GenerateEmployee {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> addEmployees(int count) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            employees.add(createEmployee());
        }
        employeeRepository.saveAll(employees);
        return employees;
    }

    private Employee createEmployee() {
        return Employee.builder()
                .uid(UUID.randomUUID())
                .firstName("fname")
                .lastName("lname")
                .status(StatusEmployee.ACTIVE)
                .build();
    }
}
