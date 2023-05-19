package digital.design.management.system.repository.impl;

import digital.design.management.system.entity.Project;
import digital.design.management.system.repository.AbstractBaseRepository;

public class ProjectRepositoryImpl extends AbstractBaseRepository<Project> {
    public ProjectRepositoryImpl(String path) {
        super(path);
    }
}
