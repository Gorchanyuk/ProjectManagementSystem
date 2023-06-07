package digital.design.management.system.test.unit.service;

import digital.design.management.system.common.enumerate.StatusProject;
import digital.design.management.system.common.exception.CanNotAssignGivenStatusException;
import digital.design.management.system.common.exception.ProjectDoesNotExistException;
import digital.design.management.system.common.exception.SuchCodeProjectAlreadyExistException;
import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.mapping.impl.ProjectMapper;
import digital.design.management.system.repository.ProjectRepository;
import digital.design.management.system.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Spy
    private ProjectMapper mapper;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    public void findByUidShouldThrowProjectDoesNotExistException() {
        UUID uid = UUID.randomUUID();
        when(projectRepository.findByUid(uid)).thenReturn(Optional.empty());

        Assertions.assertThrows(ProjectDoesNotExistException.class,
                () -> projectService.findByUid(uid));
    }

    @Test
    public void findByUidShouldReturnProject() {
        UUID uid = UUID.randomUUID();
        when(projectRepository.findByUid(uid)).thenReturn(Optional.of(new Project()));

        Assertions.assertNotNull(projectService.findByUid(uid));
    }

    @Test
    public void getProjectsShouldReturnListProjects() {
        when(projectRepository.findTop100By()).thenReturn(List.of(new Project()));

        Assertions.assertFalse(projectService.getProjects().isEmpty());
    }

    @Test
    public void createProjectShouldCreateNewProject() {
        ProjectDTO dto = ProjectDTO.builder().code("code").build();

        ProjectOutDTO outDTO = projectService.createProject(dto);

        verify(projectRepository).save(any());
        Assertions.assertNotNull(outDTO);
    }
    @Test
    public void updateProjectShouldThrowSuchCodeProjectAlreadyExistException(){
        UUID uid = UUID.randomUUID();
        Project project = Project.builder().code("code").build();
        ProjectDTO dto =ProjectDTO.builder().code("newCode").build();
        when(projectRepository.findByUid(uid)).thenReturn(Optional.of(project));
        when(projectRepository.findByCode(dto.getCode())).thenReturn(Optional.of(new Project()));

        Assertions.assertThrows(SuchCodeProjectAlreadyExistException.class,
                ()->projectService.updateProject(uid, dto));
    }

    @Test
    public void updateProjectShouldUpdateProject(){
        UUID uid = UUID.randomUUID();
        Project project = Project.builder().code("code").build();
        ProjectDTO dto =ProjectDTO.builder().code("code").name("name").build();
        when(projectRepository.findByUid(uid)).thenReturn(Optional.of(project));

        ProjectOutDTO outDTO = projectService.updateProject(uid, dto);

        verify(projectRepository).save(project);
        Assertions.assertNotNull(outDTO);
        Assertions.assertEquals(dto.getName(), outDTO.getName());
    }

    @Test
    public void getProjectsBySearchShouldFindProject(){
        when(projectRepository.findByKeyWordAndStatus(anyString(), any()))
                .thenReturn(List.of(new Project()));

        Assertions.assertFalse(projectService.getProjectsBySearch("key", List.of(StatusProject.TEST)).isEmpty());
    }

    @Test
    public void updateStatusProjectShouldThrowCanNotAssignGivenStatusException(){
        UUID uid = UUID.randomUUID();
        StatusProject status = StatusProject.TEST;
        Project project = Project.builder().status(StatusProject.DRAFT).build();
        when(projectRepository.findByUid(uid)).thenReturn(Optional.of(project));

        Assertions.assertThrows(CanNotAssignGivenStatusException.class,
                ()->projectService.updateStatusProject(uid, status));
    }

    @Test
    public void updateStatusProjectShouldUpdateStatus(){
        UUID uid = UUID.randomUUID();
        StatusProject status = StatusProject.DEVELOP;
        Project project = Project.builder().status(StatusProject.DRAFT).build();
        when(projectRepository.findByUid(uid)).thenReturn(Optional.of(project));

        ProjectOutDTO outDTO = projectService.updateStatusProject(uid, status);

        verify(projectRepository).save(project);
        Assertions.assertNotNull(outDTO);
        Assertions.assertEquals(status, project.getStatus());
    }

}
