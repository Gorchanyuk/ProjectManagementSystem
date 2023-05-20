package digital.design.management.system.common.util;


import digital.design.management.system.ProjectTeamId;
import digital.design.management.system.enumerate.StatusEmployee;
import digital.design.management.system.dto.project_team.ProjectTeamDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.repository.EmployeeRepository;
import digital.design.management.system.repository.ProjectRepository;
import digital.design.management.system.repository.ProjectTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProjectTeamValidator implements Validator {

    private final ProjectTeamRepository projectTeamRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectTeamDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectTeamDTO team = (ProjectTeamDTO) target;
        Optional<Employee> employee = employeeRepository.findByUid(team.getEmployeeUid());
        Optional<Project> project = projectRepository.findByUid(team.getProjectUid());
        if(project.isPresent() && employee.isPresent()){
            ProjectTeamId id = new ProjectTeamId(project.get().getId(), employee.get().getId());
            if (projectTeamRepository.findById(id).isPresent()) {
                errors.rejectValue("employeeId", "", "Сотрудника с таким id уже участвует в этом проекте");
            }
        }
        if(project.isEmpty()){
            errors.rejectValue("projectId", "", "Gроекта с таким id не существует");
        }
        if (employee.isEmpty()) {
            errors.rejectValue("employeeId", "", "Сотрудника с таким id не существует");
        }
        if (employee.get().getStatus().equals(StatusEmployee.DELETED)) {
            errors.rejectValue("employeeId", "", "Сотрудника с таким id удален");
        }


    }
}
