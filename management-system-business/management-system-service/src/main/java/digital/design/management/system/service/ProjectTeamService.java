package digital.design.management.system.service;


import digital.design.management.system.dto.project_team.ProjectTeamDTO;
import digital.design.management.system.dto.project_team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.project_team.ProjectTeamOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.repository.ProjectTeamRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectTeamService {

    private final ProjectTeamRepository projectTeamRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final ProjectService projectService;


    public List<ProjectTeamOutDTO> getAllParty(UUID projectId) {
        List<ProjectTeam> projectTeam = projectTeamRepository.findAllByProjectId_Uid(projectId);
        return projectTeam.stream()
                .map(team -> modelMapper.map(team, ProjectTeamOutDTO.class))
                .toList();
    }

    public ProjectTeamOutDTO addParticipant(ProjectTeamDTO projectTeamDTO) {
        //Получаем сотрудника и проект по их uid
        Employee employee = employeeService.findEmployeeByUid(projectTeamDTO.getEmployeeUid());
        Project project = projectService.findProjectByUid(projectTeamDTO.getProjectUid());
        ProjectTeam projectTeam = new ProjectTeam(project, employee,projectTeamDTO.getRoleEmployee() );
        projectTeamRepository.save(projectTeam);

        return modelMapper.map(projectTeam, ProjectTeamOutDTO.class);
    }

    public void deleteParticipant(ProjectTeamDeleteDTO projectTeamDeleteDTO) {
        Employee employee = employeeService.findEmployeeByUid(projectTeamDeleteDTO.getEmployeeUid());
        Project project = projectService.findProjectByUid(projectTeamDeleteDTO.getProjectUid());
        ProjectTeam projectTeam = new ProjectTeam(project, employee);
        projectTeamRepository.delete(projectTeam);
    }
}
