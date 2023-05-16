package didgital.design.management.system.repository.impl;

import didgital.design.management.system.entity.Employee;
import didgital.design.management.system.repository.AbstractBaseRepository;

public class EmployeeRepositoryImpl extends AbstractBaseRepository<Employee> {

    public EmployeeRepositoryImpl(String path) {
        super(path);
    }
}
