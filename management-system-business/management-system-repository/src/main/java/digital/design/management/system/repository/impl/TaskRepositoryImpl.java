package digital.design.management.system.repository.impl;

import digital.design.management.system.entity.Task;
import digital.design.management.system.repository.AbstractBaseRepository;

public class TaskRepositoryImpl extends AbstractBaseRepository<Task> {
    public TaskRepositoryImpl(String path) {
        super(path);
    }
}
