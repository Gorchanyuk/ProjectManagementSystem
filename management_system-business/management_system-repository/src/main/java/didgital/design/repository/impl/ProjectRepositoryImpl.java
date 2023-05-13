package didgital.design.repository.impl;

import didgital.design.entity.Project;
import didgital.design.repository.AbstractBaseRepository;

public class ProjectRepositoryImpl extends AbstractBaseRepository<Project> {
    public ProjectRepositoryImpl(String path) {
        super(path);
    }
}
