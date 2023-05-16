package didgital.design.management.system.repository.impl;

import didgital.design.management.system.entity.Task;
import didgital.design.management.system.repository.AbstractBaseRepository;

public class TaskRepositoryImpl extends AbstractBaseRepository<Task> {
    public TaskRepositoryImpl(String path) {
        super(path);
    }
}
