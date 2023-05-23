package digital.design.management.system.service;

import digital.design.management.system.ProjectTeamId;
import digital.design.management.system.common.exception.AuthorIsNotInvolvedInProjectException;
import digital.design.management.system.common.exception.EmployeeIsNotInvolvedInProjectException;
import digital.design.management.system.common.exception.TaskDoesNotExistException;
import digital.design.management.system.dto.task.TaskCreateDTO;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.Task;
import digital.design.management.system.enumerate.StatusTask;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.repository.TaskRepository;
import digital.design.management.system.security.EmployeeDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final Mapper<Task, TaskDTO, TaskOutDTO> mapper;
    private final ProjectTeamRepository projectTeamRepository;
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final TaskRepository taskRepository;


    @Transactional
    public TaskOutDTO createTask(TaskCreateDTO taskDTO, EmployeeDetails author) {
        Task task = mapper.dtoToEntity(taskDTO);
        Project project = projectService.findByUid(taskDTO.getProject());
        //Проверяем является ли автор участником проекта
        isProjectParticipant(project, author.getEmployee(), true);
        //Если исполнитель назначен проверяем является ли он участником проекта
        if (taskDTO.getTaskPerformer() != null) {
            Employee taskPerformer = employeeService.findByUid(taskDTO.getTaskPerformer());
            isProjectParticipant(project, taskPerformer, false);
            task.setTaskPerformer(taskPerformer);
        }
        task.setAuthor(author.getEmployee());
        task.setProject(project);
        task.setDateOfCreated(LocalDate.now());
        task.setDateOfUpdate(LocalDate.now());
        task.setStatus(StatusTask.NEW);
        taskRepository.save(task);

        return mapper.entityToOutDto(task);
    }

    public TaskOutDTO updateTask(UUID uid, TaskDTO taskDTO, EmployeeDetails author) {
        Task task = taskRepository.findByUid(uid)
                .orElseThrow(TaskDoesNotExistException::new);
        Project project = task.getProject();
        //Проверяем является ли автор участником проекта
        isProjectParticipant(project, author.getEmployee(), true);
        //Проверяем изменился ли исполнитель, если да, проверяем участник ли он команды
        if (taskDTO.getTaskPerformer() != null &&
                taskDTO.getTaskPerformer().equals(task.getTaskPerformer().getUid())) {
            Employee taskPerformer = employeeService.findByUid(taskDTO.getTaskPerformer());
            isProjectParticipant(project, taskPerformer, false);
            task.setTaskPerformer(taskPerformer);
        }
        task = mapper.dtoToEntity(taskDTO, task);
        taskRepository.save(task);

        return mapper.entityToOutDto(task);

    }

    private void isProjectParticipant(Project project, Employee employee, Boolean isAuthor) {
        ProjectTeamId projectTeamId = new ProjectTeamId(project.getId(), employee.getId());
        if (!projectTeamRepository.existsById(projectTeamId)) {
            if (isAuthor)
                throw new AuthorIsNotInvolvedInProjectException();
            else
                throw new EmployeeIsNotInvolvedInProjectException();
        }
    }
}
