package digital.design.management.system.test.unit.mapping;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.dto.team.ProjectTeamDTO;
import digital.design.management.system.dto.team.ProjectTeamOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.mapping.impl.EmployeeMapper;
import digital.design.management.system.mapping.impl.ProjectTeamMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ProjectTeamMapperTest {

    private static ProjectTeamMapper mapper;

    @BeforeAll
    public static void init(){
        mapper = new ProjectTeamMapper(new EmployeeMapper());
    }

    @Test
    public void shouldReturnNewProjectTeam(){
        ProjectTeam projectTeam = mapper.dtoToEntity(getProjectTeamDTO());

        Assertions.assertNotNull(projectTeam.getProjectId());
        Assertions.assertNotNull(projectTeam.getEmployeeId());
        Assertions.assertNotNull(projectTeam.getRoleEmployee());
    }

    @Test
    public void shouldReturnUpdatedProjectTeam(){
        ProjectTeam projectTeam = getProjectTeam();
        ProjectTeam projectTeamUpdated = mapper.dtoToEntity(getProjectTeamDTO(), getProjectTeam());

        Assertions.assertNotEquals(projectTeam, projectTeamUpdated);
    }

    @Test
    public void shouldReturnProjectTeamOutDto(){
        ProjectTeamOutDTO outDTO = mapper.entityToOutDto(getProjectTeam());

        Assertions.assertNotNull(outDTO.getEmployee());
        Assertions.assertNotNull(outDTO.getRoleEmployee());
    }

    @Test
    public void shouldReturnProjectTeamDto(){
        ProjectTeamDTO dto = mapper.entityToDto(getProjectTeam());

        Assertions.assertNotNull(dto.getProjectUid());
        Assertions.assertNotNull(dto.getEmployeeUid());
        Assertions.assertNotNull(dto.getRoleEmployee());
    }

    private ProjectTeamDTO getProjectTeamDTO(){
        return ProjectTeamDTO.builder()
                .projectUid(UUID.randomUUID())
                .employeeUid(UUID.randomUUID())
                .roleEmployee(RoleEmployee.DEVELOPER)
                .build();
    }

    private ProjectTeam getProjectTeam(){
        return ProjectTeam.builder()
                .projectId(Project.builder().uid(UUID.randomUUID()).build())
                .employeeId(Employee.builder().uid(UUID.randomUUID()).build())
                .roleEmployee(RoleEmployee.DEVELOPER)
                .build();
    }
}
