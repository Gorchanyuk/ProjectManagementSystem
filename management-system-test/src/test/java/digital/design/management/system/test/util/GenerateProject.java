package digital.design.management.system.test.util;

import digital.design.management.system.common.enumerate.StatusProject;
import digital.design.management.system.entity.Project;
import digital.design.management.system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class GenerateProject {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> addProjects(int count){
        List<Project> projects = new ArrayList<>();
        for(int i = 0; i < count; i++){
            projects.add(createProject());
        }
        projectRepository.saveAll(projects);
        return projects;
    }

    private Project createProject(){
        return Project.builder()
                .uid(UUID.randomUUID())
                .code(UUID.randomUUID().toString())
                .name("name")
                .status(StatusProject.DRAFT)
                .build();
    }
}
