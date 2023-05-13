package didgital.design.repository.impl;

import didgital.design.entity.Employee;
import didgital.design.repository.AbstractBaseRepository;

public class EmployeeRepositoryImpl extends AbstractBaseRepository<Employee> {

    public EmployeeRepositoryImpl(String path) {
        super(path);
    }
}
