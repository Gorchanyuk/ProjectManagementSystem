package digital.design.management.system.service;

import digital.design.management.system.common.exception.EntityDoesNotExistException;
import digital.design.management.system.common.exception.StatusProjectHasNotNextStatusException;
import digital.design.management.system.common.exception.SuchCodeProjectAlreadyExistException;
import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.enumerate.StatusProject;
import digital.design.management.system.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public Project findByUid(UUID uid) {

        return projectRepository.findByUid(uid)
                .orElseThrow(EntityDoesNotExistException::new);
    }

    public List<ProjectOutDTO> getProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectOutDTO.class))
                .toList();
    }

    public ProjectOutDTO createProject(ProjectDTO projectDTO) {
        Project project = modelMapper.map(projectDTO, Project.class);
        project.setStatus(StatusProject.DRAFT);
        projectRepository.save(project);

        return modelMapper.map(project, ProjectOutDTO.class);
    }

    public ProjectOutDTO updateProject(UUID uid, ProjectDTO projectDTO) {
        Project project = findByUid(uid);
        //Проверка уникальности кода проекта, если его изменили
        if (!project.getCode().equals(projectDTO.getCode())
                && projectRepository.findByCode(projectDTO.getCode()).isPresent()) {
            throw new SuchCodeProjectAlreadyExistException();
        }
        modelMapper.map(projectDTO, project);
        projectRepository.save(project);

        return modelMapper.map(project, ProjectOutDTO.class);
    }

    public List<ProjectOutDTO> getProjectsBySearch(String key, List<StatusProject> statuses) {
        List<Project> projects =
                projectRepository.findByKeyWordAndStatus(key, statuses);

        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectOutDTO.class))
                .toList();
    }

    public ProjectOutDTO updateStatusProject(UUID uid) {
        Project project = findByUid(uid);

        //Повышаем статус проекта если это возможно,
        //в противном случае выкидываем исключение
        if (project.getStatus().hasNextStatus())
            project.setStatus(project.getStatus().getNextSatatus());
        else
            throw new StatusProjectHasNotNextStatusException();

        projectRepository.save(project);
        return modelMapper.map(project, ProjectOutDTO.class);
    }


}
