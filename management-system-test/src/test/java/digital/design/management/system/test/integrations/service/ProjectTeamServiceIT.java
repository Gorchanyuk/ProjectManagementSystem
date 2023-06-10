package digital.design.management.system.test.integrations.service;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.dto.team.ProjectTeamDTO;
import digital.design.management.system.dto.team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.team.ProjectTeamOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.service.ProjectTeamService;
import digital.design.management.system.test.integrations.BaseTest;
import digital.design.management.system.test.util.GenerateEmployee;
import digital.design.management.system.test.util.GenerateProject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectTeamServiceIT extends BaseTest {

    @Autowired
    private GenerateEmployee generateEmployee;
    @Autowired
    private GenerateProject generateProject;
    @Autowired
    private ProjectTeamService projectTeamService;
    @Autowired
    private ProjectTeamRepository projectTeamRepository;

    @Test
    public void getAllPartyShouldGetAllParty() {
        Project project = generateProject.addProjects(1).get(0);
        int count = 10;
        List<Employee> employees = generateEmployee.addEmployees(count);
        List<ProjectTeam> projectTeams = new ArrayList<>();
        for (Employee employee : employees)
            projectTeams.add(new ProjectTeam(project, employee, RoleEmployee.DEVELOPER));
        projectTeamRepository.saveAll(projectTeams);

        List<ProjectTeamOutDTO> teamOutDTOS = projectTeamService.getAllParty(project.getUid());

        Assertions.assertEquals(count, teamOutDTOS.size());
    }

    @Test
    public void addParticipantShouldAddNewParticipant(){
        Project project = generateProject.addProjects(1).get(0);
        Employee employee = generateEmployee.addEmployees(1).get(0);
        ProjectTeamDTO dto = new ProjectTeamDTO(project.getUid(), employee.getUid(), RoleEmployee.DEVELOPER);

        projectTeamService.addParticipant(dto);
        List<ProjectTeam> projectTeams = projectTeamRepository.findAll();

        Assertions.assertFalse(projectTeams.isEmpty());
    }

    @Test
    public void deleteParticipantShouldDeleteParticipant(){
        Project project = generateProject.addProjects(1).get(0);
        Employee employee = generateEmployee.addEmployees(1).get(0);
        ProjectTeam projectTeam = new ProjectTeam(project, employee, RoleEmployee.DEVELOPER);
        projectTeamRepository.save(projectTeam);
        ProjectTeamDeleteDTO deleteDTO = new ProjectTeamDeleteDTO(project.getUid(), employee.getUid());

        projectTeamService.deleteParticipant(deleteDTO);
        List<ProjectTeamOutDTO> teamOutDTOS = projectTeamService.getAllParty(project.getUid());

        Assertions.assertTrue(teamOutDTOS.isEmpty());

    }


}
