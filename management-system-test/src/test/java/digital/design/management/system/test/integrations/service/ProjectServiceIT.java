package digital.design.management.system.test.integrations.service;

import digital.design.management.system.common.enumerate.StatusProject;
import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.ProjectRepository;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.test.integrations.BaseTest;
import digital.design.management.system.test.util.GenerateProject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectServiceIT extends BaseTest {

    @Autowired
    private GenerateProject generateProject;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private Mapper<Project, ProjectDTO, ProjectOutDTO> mapper;

    @Test
    public void getProjectsShouldGetListProject(){
        int count = 10;
        generateProject.addProjects(count);
        List<ProjectOutDTO> projects = projectService.getProjects();

        Assertions.assertEquals(count, projects.size());
    }

    @Test
    public void findByUidShouldGetProject(){
        List<Project> projects = generateProject.addProjects(10);
        UUID uid = projects.get(0).getUid();

        Project project = projectService.findByUid(uid);

        Assertions.assertEquals(uid, project.getUid());
    }

    @Test
    public void createProjectShouldCreateNewProject(){
        ProjectDTO dto = ProjectDTO.builder()
                .code(UUID.randomUUID().toString())
                .name("name")
                .description("description")
                .build();
        ProjectOutDTO outDTO = projectService.createProject(dto);
        Optional<Project> project = projectRepository.findByUid(outDTO.getUid());

        Assertions.assertTrue(project.isPresent());
    }

    @Test
    public void updateProjectShouldUpdateProject(){
        Project project = generateProject.addProjects(1).get(0);
        ProjectOutDTO outDTO = mapper.entityToOutDto(project);

        ProjectDTO dto = mapper.entityToDto(project);
        String name = "newName";
        dto.setName(name);
        UUID uid = project.getUid();

        projectService.updateProject(uid, dto);
        Optional<Project> projectUpdate = projectRepository.findByUid(uid);

        Assertions.assertTrue(projectUpdate.isPresent());
        ProjectOutDTO outDTOUpdate = mapper.entityToOutDto(projectUpdate.get());

        Assertions.assertNotEquals(outDTO, outDTOUpdate);
    }

    @Test
    public void getProjectsBySearchShouldFindProjects(){
        List<Project> projects = generateProject.addProjects(10);
        Project project1 = projects.get(0);
        Project project2 = projects.get(1);
        project1.setName("Develop frontend");
        project2.setName("Develop backend");
        project1.setStatus(StatusProject.TEST);
        project2.setStatus(StatusProject.DEVELOP);
        projectRepository.saveAll(List.of(project1, project2));

        List<ProjectOutDTO> searchProjects = projectService.getProjectsBySearch(
                "dev",
                List.of(StatusProject.DEVELOP, StatusProject.TEST));

        Assertions.assertEquals(2, searchProjects.size());
    }

    @Test
    public void updateStatusProjectShouldUpdateStatus(){
        Project project = generateProject.addProjects(1).get(0);
        UUID uid = project.getUid();
        StatusProject statusUpdate = project.getStatus().getNextStatus();
        projectService.updateStatusProject(uid, statusUpdate);
        Project projectUpdate = projectRepository.findByUid(uid).get();

        Assertions.assertEquals(statusUpdate, projectUpdate.getStatus());
    }

}
