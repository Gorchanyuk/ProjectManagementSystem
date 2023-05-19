package digital.design.management.system.repository.impl;

import digital.design.management.system.entity.Employee;
import digital.design.management.system.repository.AbstractBaseRepository;

public class EmployeeRepositoryImpl extends AbstractBaseRepository<Employee> {

    public EmployeeRepositoryImpl(String path) {
        super(path);
    }
}
