package digital.design.management.system.test.unit.service;

import digital.design.management.system.common.exception.EmployeeAlreadyParticipatingInProjectException;
import digital.design.management.system.common.exception.EmployeeIsNotInvolvedInProjectException;
import digital.design.management.system.dto.team.ProjectTeamDTO;
import digital.design.management.system.dto.team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.team.ProjectTeamOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.mapping.impl.EmployeeMapper;
import digital.design.management.system.mapping.impl.ProjectTeamMapper;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.service.impl.ProjectTeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProjectTeamServiceTest {

    @Mock
    private ProjectTeamRepository projectTeamRepository;
    @Spy
    private ProjectTeamMapper projectTeamMapper = new ProjectTeamMapper(new EmployeeMapper());
    @Mock
    private EmployeeService employeeService;
    @Mock
    private ProjectService projectService;
    @InjectMocks
    ProjectTeamServiceImpl projectTeamService;

    @Test
    public void getAllPartyShouldReturnAllEmployeeFromProject(){
        UUID uid = UUID.randomUUID();
        Employee employee = Employee.builder().build();
        ProjectTeam team = ProjectTeam.builder().employeeId(employee).build();
        when(projectTeamRepository.findAllByProjectId_Uid(uid)).thenReturn(List.of(team));

        Assertions.assertFalse(projectTeamService.getAllParty(uid).isEmpty());
    }

    @Test
    public void addParticipantShouldEmployeeAlreadyParticipatingInProjectException(){
        ProjectTeamDTO dto = new ProjectTeamDTO();
        when(employeeService.findByUid(any())).thenReturn(new Employee());
        when(projectService.findByUid(any())).thenReturn(new Project());
        when(projectTeamRepository.findById(any())).thenReturn(Optional.of(new ProjectTeam()));

        Assertions.assertThrows(EmployeeAlreadyParticipatingInProjectException.class,
                ()->projectTeamService.addParticipant(dto));
    }

    @Test
    public void addParticipantShouldAddNewParticipant(){
        ProjectTeamDTO dto = new ProjectTeamDTO();
        when(employeeService.findByUid(any())).thenReturn(new Employee());
        when(projectService.findByUid(any())).thenReturn(new Project());
        when(projectTeamRepository.findById(any())).thenReturn(Optional.empty());

        ProjectTeamOutDTO outDto = projectTeamService.addParticipant(dto);

        verify(projectTeamRepository).save(any());
        Assertions.assertNotNull(outDto);
    }

    @Test
    public void deleteParticipantShouldThrowEmployeeIsNotInvolvedInProjectException(){
        ProjectTeamDeleteDTO deleteDTO = new ProjectTeamDeleteDTO();
        when(projectTeamRepository.findByProjectId_UidAndEmployeeId_Uid(any(), any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EmployeeIsNotInvolvedInProjectException.class,
                ()->projectTeamService.deleteParticipant(deleteDTO));
    }

    @Test
    public void deleteParticipantShouldDeleteEmployeeFromTeam(){
        ProjectTeamDeleteDTO deleteDTO = new ProjectTeamDeleteDTO();
        when(projectTeamRepository.findByProjectId_UidAndEmployeeId_Uid(any(), any()))
                .thenReturn(Optional.of(new ProjectTeam()));

        projectTeamService.deleteParticipant(deleteDTO);

        verify(projectTeamRepository).delete(any());
    }

}
