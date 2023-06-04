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
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.service.TaskService;
import digital.design.management.system.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final Mapper<Task, TaskDTO, TaskOutDTO> mapper;
    private final ProjectTeamRepository projectTeamRepository;
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final TaskRepository taskRepository;


    @Override
    @Transactional
    public TaskOutDTO createTask(TaskCreateDTO taskDTO, Employee author) {
        log.debug("Create a task");
        Task task = mapper.dtoToEntity(taskDTO);
        Project project = projectService.findByUid(taskDTO.getProject());
        log.debug("Project received");
        //Проверяем является ли автор участником проекта
        isProjectParticipant(project, author, true);
        //Если исполнитель назначен проверяем является ли он участником проекта
        if (task.getTaskPerformer() != null) {
            Employee taskPerformer = employeeService.findByUid(task.getTaskPerformer().getUid());
            isProjectParticipant(project, taskPerformer, false);
            task.setTaskPerformer(taskPerformer);
        }
        task.setAuthor(author);
        task.setProject(project);
        task.setDateOfCreated(LocalDate.now());
        task.setDateOfUpdate(LocalDate.now());
        task.setStatus(StatusTask.NEW);
        task.setUid(UUID.randomUUID());
        taskRepository.save(task);
        log.info("Task created successfully");
        return mapper.entityToOutDto(task);
    }

    @Override
    public TaskOutDTO updateTask(UUID uid, TaskDTO taskDTO, Employee author) {
        log.debug("Update a task with uid:{}", uid);
        Task task = findByUid(uid);
        log.debug("Task received by uid: {}", uid);
        //сохраняем значения исполнителя который был до изменения задачи
        Employee performerBeforeUpdate = task.getTaskPerformer();
        task = mapper.dtoToEntity(taskDTO, task);
        Project project = task.getProject();
        //Проверяем является ли автор участником проекта
        isProjectParticipant(project, author, true);
        //Проверяем изменился ли исполнитель, если да, проверяем участник ли он команды
        if (task.getTaskPerformer() != null) {
            if (performerBeforeUpdate != null &&
                    task.getTaskPerformer().getUid().equals(performerBeforeUpdate.getUid())) {
                task.setTaskPerformer(performerBeforeUpdate);
            } else {
                Employee taskPerformer = employeeService.findByUid(taskDTO.getTaskPerformer());
                isProjectParticipant(project, taskPerformer, false);
                task.setTaskPerformer(taskPerformer);
            }
        }
        task.setAuthor(author);
        task.setDateOfUpdate(LocalDate.now());
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
        Task task = findByUid(uid);
        log.debug("Task received");
        if (!task.getStatus().hasNextStatus() ||
                !task.getStatus().getNextStatus().equals(statusTask))
            throw new CanNotAssignGivenStatusException();

        task.setStatus(statusTask);
        taskRepository.save(task);
        log.info("Status task with uid: {} updated", uid);
        return mapper.entityToOutDto(task);
    }

    private Task findByUid(UUID uid){
        return taskRepository.findByUid(uid).orElseThrow(TaskDoesNotExistException::new);
    }
}
