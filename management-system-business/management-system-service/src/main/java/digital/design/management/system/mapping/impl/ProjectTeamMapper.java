package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.team.ProjectTeamDTO;
import digital.design.management.system.dto.team.ProjectTeamOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.mapping.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class ProjectTeamMapper implements Mapper<ProjectTeam, ProjectTeamDTO, ProjectTeamOutDTO> {

    private final EmployeeMapper employeeMapper;

    @Override
    public ProjectTeam dtoToEntity(ProjectTeamDTO dto){
        log.debug("Mapping ProjectTeamDTO to ProjectTeam");
        return ProjectTeam.builder()
                .projectId(Project.builder().uid(dto.getProjectUid()).build())
                .employeeId(Employee.builder().uid(dto.getEmployeeUid()).build())
                .roleEmployee(dto.getRoleEmployee())
                .build();
    }

    @Override
    public ProjectTeam dtoToEntity(ProjectTeamDTO dto, ProjectTeam projectTeam) {
        log.debug("Mapping an ProjectTeamDTO to an ProjectTeam in a given projectTeam");
        projectTeam.setRoleEmployee(dto.getRoleEmployee());
        return projectTeam;
    }

    @Override
    public ProjectTeamOutDTO entityToOutDto(ProjectTeam team){
        log.debug("Mapping ProjectTeam to ProjectTeamOutDTO");
        return ProjectTeamOutDTO.builder()
                .employeeId(employeeMapper.entityToOutDto(team.getEmployeeId()))
                .roleEmployee(team.getRoleEmployee())
                .build();
    }

    @Override
    public ProjectTeamDTO entityToDto(ProjectTeam entity) {
        return ProjectTeamDTO.builder()
                .projectUid(entity.getProjectId().getUid())
                .employeeUid(entity.getEmployeeId().getUid())
                .roleEmployee(entity.getRoleEmployee())
                .build();
    }
}
