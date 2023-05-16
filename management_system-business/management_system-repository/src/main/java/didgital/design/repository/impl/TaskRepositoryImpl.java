package didgital.design.repository.impl;

import didgital.design.entity.Task;
import didgital.design.repository.AbstractBaseRepository;

public class TaskRepositoryImpl extends AbstractBaseRepository<Task> {
    public TaskRepositoryImpl(String path) {
        super(path);
    }
}
