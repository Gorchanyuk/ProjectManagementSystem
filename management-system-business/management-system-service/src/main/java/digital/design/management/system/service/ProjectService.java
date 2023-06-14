package digital.design.management.system.service;

import digital.design.management.system.common.enumerate.StatusProject;
import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.entity.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    Project findByUid(UUID uid);

    List<ProjectOutDTO> getProjects();

    ProjectOutDTO createProject(ProjectDTO projectDTO);

    ProjectOutDTO updateProject(UUID uid, ProjectDTO projectDTO);

    List<ProjectOutDTO> getProjectsBySearch(String key, List<StatusProject> statuses);

    ProjectOutDTO updateStatusProject(UUID uid, StatusProject statusProject);


}
