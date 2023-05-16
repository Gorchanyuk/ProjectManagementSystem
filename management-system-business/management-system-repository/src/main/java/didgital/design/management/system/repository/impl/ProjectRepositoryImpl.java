package didgital.design.management.system.repository.impl;

import didgital.design.management.system.entity.Project;
import didgital.design.management.system.repository.AbstractBaseRepository;

public class ProjectRepositoryImpl extends AbstractBaseRepository<Project> {
    public ProjectRepositoryImpl(String path) {
        super(path);
    }
}
