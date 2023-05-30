package digital.design.management.system.service.impl;

import digital.design.management.system.dto.util.ProjectTeamId;
import digital.design.management.system.common.exception.*;
import digital.design.management.system.dto.task.TaskCreateDTO;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskFilterDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.Task;
import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.repository.TaskRepository;
import digital.design.management.system.security.EmployeeDetails;
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.service.TaskService;
import digital.design.management.system.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {

    private final Mapper<Task, TaskDTO, TaskOutDTO> mapper;
    private final ProjectTeamRepository projectTeamRepository;
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final TaskRepository taskRepository;


    @Override
    @Transactional
    public TaskOutDTO createTask(TaskCreateDTO taskDTO, EmployeeDetails author) {
        log.debug("Create a task");
        Task task = mapper.dtoToEntity(taskDTO);
        Project project = projectService.findByUid(taskDTO.getProject());
        log.debug("Project received");
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
        log.info("Task created successfully");
        return mapper.entityToOutDto(task);
    }

    @Override
    public TaskOutDTO updateTask(UUID uid, TaskDTO taskDTO, EmployeeDetails author) {
        log.debug("Update a task with uid:{}", uid);
        Task task = taskRepository.findByUid(uid)
                .orElseThrow(TaskDoesNotExistException::new);
        log.debug("Task received by uid: {}", uid);
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
        task.setAuthor(author.getEmployee());
        task.setDateOfUpdate(LocalDate.now());
        task = mapper.dtoToEntity(taskDTO, task);
        taskRepository.save(task);

        log.info("Task with uid: {} updated", uid);
        return mapper.entityToOutDto(task);

    }

    @Override
    public List<TaskOutDTO> getTasksWithFilter(TaskFilterDTO taskFilterDTO) {
        log.debug("Search for a tasks by filter");
        List<Task> tasks = taskRepository.findAll(
                TaskSpecification.getSpecification(taskFilterDTO),
                Sort.by("dateOfCreated").descending());
        log.info("Tasks found");
        return tasks.stream().map(mapper::entityToOutDto).toList();
    }

    private void isProjectParticipant(Project project, Employee employee, Boolean isAuthor) {
        log.debug("Checking if an employee is a team member");
        ProjectTeamId projectTeamId = new ProjectTeamId(project.getId(), employee.getId());
        if (!projectTeamRepository.existsById(projectTeamId)) {
            if (isAuthor)
                throw new AuthorIsNotInvolvedInProjectException();
            else
                throw new EmployeeIsNotInvolvedInProjectException();
        }
    }

    @Override
    public TaskOutDTO updateStatusTask(UUID uid, StatusTask statusTask) {
        log.debug("Update status task with uid: {}", uid);
        Task task = taskRepository.findByUid(uid).orElseThrow(TaskDoesNotExistException::new);
        log.debug("Task received");
        if (!task.getStatus().hasNextStatus())
            throw new MaximumTaskStatusException();
        if (!task.getStatus().getNextStatus().equals(statusTask)) {
            throw new CanNotAssignGivenTaskStatusException();
        }
        task.setStatus(statusTask);
        taskRepository.save(task);
        log.info("Status task with uid: {} updated", uid);
        return mapper.entityToOutDto(task);
    }
}
