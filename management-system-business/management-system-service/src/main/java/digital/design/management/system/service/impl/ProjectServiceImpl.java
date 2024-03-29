package digital.design.management.system.service.impl;

import digital.design.management.system.common.exception.CanNotAssignGivenStatusException;
import digital.design.management.system.common.exception.SuchCodeProjectAlreadyExistException;
import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.common.enumerate.StatusProject;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.ProjectRepository;
import digital.design.management.system.service.ProjectService;
import lombok.RequiredArgsConstructor;
import digital.design.management.system.common.exception.ProjectDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final Mapper<Project, ProjectDTO, ProjectOutDTO> mapper;

    @Override
    public Project findByUid(UUID uid) {
        log.debug("Search for the project with uid: {}", uid);
        Project project = projectRepository.findByUid(uid)
                .orElseThrow(ProjectDoesNotExistException::new);
        log.info("Project with uid: {} found", uid);
        return project;
    }

    @Override
    public List<ProjectOutDTO> getProjects() {
        List<Project> projects = projectRepository.findTop100By();
        log.info("All projects found");
        return projects.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    @Override
    public ProjectOutDTO createProject(ProjectDTO projectDTO) {
        Project project = mapper.dtoToEntity(projectDTO);
        UUID uid = UUID.randomUUID();
        project.setUid(uid);
        log.debug("Create new Project, with uid: {}", uid);
        project.setStatus(StatusProject.DRAFT);
        projectRepository.save(project);
        log.info("New Project created, uid: {}", uid);
        return mapper.entityToOutDto(project);
    }

    @Override
    public ProjectOutDTO updateProject(UUID uid, ProjectDTO projectDTO) {
        log.debug("Update Project with uid:{}", uid);
        Project project = findByUid(uid);
        //Проверка уникальности кода проекта, если его изменили
        if (!project.getCode().equals(projectDTO.getCode())
                && projectRepository.findByCode(projectDTO.getCode()).isPresent()) {
            throw new SuchCodeProjectAlreadyExistException();
        }
        project = mapper.dtoToEntity(projectDTO, project);
        projectRepository.save(project);
        log.info("Project with uid: {} updated", uid);
        return mapper.entityToOutDto(project);
    }

    @Override
    public List<ProjectOutDTO> getProjectsBySearch(String key, List<StatusProject> statuses) {
        List<Project> projects =
                projectRepository.findByKeyWordAndStatus(key, statuses);
        log.info("All Projects with keyword: {}, and statuses: {} found ", key, statuses);
        return projects.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    @Override
    public ProjectOutDTO updateStatusProject(UUID uid, StatusProject statusProject) {
        log.debug("Update 'statusProject' for Project with uid:{}", uid);
        Project project = findByUid(uid);

        //Повышаем статус проекта если это возможно,
        //в противном случае выкидываем исключение
        if (!project.getStatus().hasNextStatus() ||
                !project.getStatus().getNextStatus().equals(statusProject))
            throw new CanNotAssignGivenStatusException();

        project.setStatus(statusProject);
        projectRepository.save(project);
        log.info("Status for Project with uid: {} update", uid);
        return mapper.entityToOutDto(project);
    }
}
