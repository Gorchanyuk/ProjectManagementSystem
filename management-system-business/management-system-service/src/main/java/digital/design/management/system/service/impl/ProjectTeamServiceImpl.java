package digital.design.management.system.service.impl;


import digital.design.management.system.dto.util.ProjectTeamId;
import digital.design.management.system.common.exception.EmployeeAlreadyParticipatingInProjectException;
import digital.design.management.system.common.exception.EmployeeIsNotInvolvedInProjectException;
import digital.design.management.system.dto.team.ProjectTeamDTO;
import digital.design.management.system.dto.team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.team.ProjectTeamOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.service.ProjectTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProjectTeamServiceImpl implements ProjectTeamService {

    private final ProjectTeamRepository projectTeamRepository;
    private final Mapper<ProjectTeam, ProjectTeamDTO, ProjectTeamOutDTO> mapper;
    private final EmployeeService employeeService;
    private final ProjectService projectService;


    @Override
    public List<ProjectTeamOutDTO> getAllParty(UUID projectUid) {
        List<ProjectTeam> projectTeam = projectTeamRepository.findAllByProjectId_Uid(projectUid);
        log.info("All ProjectTeam found");
        return projectTeam.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    @Override
    public ProjectTeamOutDTO addParticipant(ProjectTeamDTO projectTeamDTO) {
        log.debug("Adding an employee to a team");
        //Получаем сотрудника и проект по их uid
        Employee employee = employeeService.findByUid(projectTeamDTO.getEmployeeUid());
        log.debug("Employee received");
        Project project = projectService.findByUid(projectTeamDTO.getProjectUid());
        log.debug("Project received");
        ProjectTeamId id = new ProjectTeamId(project.getId(), employee.getId());

        //Проверяем чтобы такой записи не было в БД;
        Optional<ProjectTeam> projectTeamOptional = projectTeamRepository.findById(id);
        if (projectTeamOptional.isPresent())
            throw new EmployeeAlreadyParticipatingInProjectException();

        ProjectTeam projectTeam = new ProjectTeam(project, employee, projectTeamDTO.getRoleEmployee());
        projectTeamRepository.save(projectTeam);
        log.info("The employee was successfully added to the team");
        return mapper.entityToOutDto(projectTeam);
    }

    @Override
    public void deleteParticipant(ProjectTeamDeleteDTO deleteDTO) {
        log.debug("Removing an employee from a team");
        ProjectTeam projectTeam = projectTeamRepository
                .findByProjectId_UidAndEmployeeId_Uid(deleteDTO.getProjectUid(), deleteDTO.getEmployeeUid())
                .orElseThrow(EmployeeIsNotInvolvedInProjectException::new);
        projectTeamRepository.delete(projectTeam);
        log.info("Employee removed from team");
    }
}