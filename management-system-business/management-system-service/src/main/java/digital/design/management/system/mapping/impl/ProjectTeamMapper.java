package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.project_team.ProjectTeamDTO;
import digital.design.management.system.dto.project_team.ProjectTeamOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.mapping.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectTeamMapper implements Mapper<ProjectTeam, ProjectTeamDTO, ProjectTeamOutDTO> {

    private final EmployeeMapper employeeMapper;

    @Override
    public ProjectTeam dtoToEntity(ProjectTeamDTO dto){

        return ProjectTeam.builder()
                .projectId(Project.builder().uid(dto.getProjectUid()).build())
                .employeeId(Employee.builder().uid(dto.getEmployeeUid()).build())
                .roleEmployee(dto.getRoleEmployee())
                .build();
    }

    @Override
    public ProjectTeam dtoToEntity(ProjectTeamDTO dto, ProjectTeam projectTeam) {

        projectTeam.setRoleEmployee(dto.getRoleEmployee());
        return projectTeam;
    }

    @Override
    public ProjectTeamOutDTO entityToOutDto(ProjectTeam team){

        return ProjectTeamOutDTO.builder()
                .employeeId(employeeMapper.entityToOutDto(team.getEmployeeId()))
                .roleEmployee(team.getRoleEmployee())
                .build();
    }
}
