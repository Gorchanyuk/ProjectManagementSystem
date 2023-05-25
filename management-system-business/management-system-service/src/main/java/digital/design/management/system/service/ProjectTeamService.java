package digital.design.management.system.service;


import digital.design.management.system.ProjectTeamId;
import digital.design.management.system.common.exception.EmployeeAlreadyParticipatingInProjectException;
import digital.design.management.system.common.exception.EmployeeIsNotInvolvedInProjectException;
import digital.design.management.system.dto.project_team.ProjectTeamDTO;
import digital.design.management.system.dto.project_team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.project_team.ProjectTeamOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.ProjectTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectTeamService {

    private final ProjectTeamRepository projectTeamRepository;
    private final Mapper<ProjectTeam, ProjectTeamDTO, ProjectTeamOutDTO> mapper;
    private final EmployeeService employeeService;
    private final ProjectService projectService;


    public List<ProjectTeamOutDTO> getAllParty(UUID projectUid) {
        List<ProjectTeam> projectTeam = projectTeamRepository.findAllByProjectId_Uid(projectUid);

        return projectTeam.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    public ProjectTeamOutDTO addParticipant(ProjectTeamDTO projectTeamDTO) {
        //Получаем сотрудника и проект по их uid
        Employee employee = employeeService.findByUid(projectTeamDTO.getEmployeeUid());
        Project project = projectService.findByUid(projectTeamDTO.getProjectUid());
        ProjectTeamId id = new ProjectTeamId(project.getId(), employee.getId());

        //Проверяем чтобы такой записи не было в БД;
        Optional<ProjectTeam> projectTeamOptional = projectTeamRepository.findById(id);
        if (projectTeamOptional.isPresent())
            throw new EmployeeAlreadyParticipatingInProjectException();

        ProjectTeam projectTeam = new ProjectTeam(project, employee, projectTeamDTO.getRoleEmployee());
        projectTeamRepository.save(projectTeam);

        return mapper.entityToOutDto(projectTeam);
    }

    public void deleteParticipant(ProjectTeamDeleteDTO deleteDTO) {

        //Вариант 1
        Employee employee = employeeService.findByUid(deleteDTO.getEmployeeUid());
        Project project = projectService.findByUid(deleteDTO.getProjectUid());
        ProjectTeamId id = new ProjectTeamId(project.getId(), employee.getId());
        ProjectTeam projectTeam = projectTeamRepository.findById(id)
                .orElseThrow(EmployeeIsNotInvolvedInProjectException::new);
        projectTeamRepository.delete(projectTeam);


        //Варриант 2
//        ProjectTeam projectTeam = projectTeamRepository
//                .findByProjectId_UidAndEmployeeId_Uid(deleteDTO.getProjectUid(), deleteDTO.getEmployeeUid())
//                .orElseThrow(EmployeeIsNotInvolvedInProjectException::new);
//        projectTeamRepository.delete(projectTeam);


        //Вариант 3
//        projectTeamRepository.deleteByProjectId_UidAndEmployeeId_Uid(deleteDTO.getProjectUid(), deleteDTO.getEmployeeUid());
    }


}
