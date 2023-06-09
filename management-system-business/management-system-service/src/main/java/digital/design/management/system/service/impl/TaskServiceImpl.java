package digital.design.management.system.service.impl;

import digital.design.management.system.dto.mail.EmailDTO;
import digital.design.management.system.dto.task.*;
import digital.design.management.system.dto.util.ProjectTeamId;
import digital.design.management.system.common.exception.*;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.Task;
import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.rabbitmq.MessageProducer;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.repository.TaskRepository;
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.service.TaskService;
import digital.design.management.system.specification.TaskSpecification;
import digital.design.management.system.util.CreatorMailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
    private final MessageProducer messageProducer;
    private final CreatorMailDTO creatorMailDTO;
    private final ResourceBundle resourceBundle;


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
        if (!ObjectUtils.isEmpty(task.getTaskPerformer())) {
            setPerformerAndSendMail(taskDTO.getTaskPerformer(), project, task);
        }
        task.setAuthor(author);
        task.setProject(project);
        task.setDateOfCreated(LocalDate.now());
        task.setDateOfUpdate(LocalDate.now());
        task.setStatus(StatusTask.NEW);
        task.setUid(UUID.randomUUID());
        taskRepository.save(task);
        log.info("Task {} created successfully", task.getUid());
        return mapper.entityToOutDto(task);
    }

    @Override
    public TaskOutDTO updateTask(UUID uid, TaskDTO taskDTO, Employee author) {
        log.debug("Update a task with uid:{}", uid);
        Task task = findByUid(uid);
        log.debug("Task received by uid: {}", uid);
        Employee performerBeforeUpdate = task.getTaskPerformer();//сохраняем значения исполнителя который был до изменения задачи
        task = mapper.dtoToEntity(taskDTO, task);
        Project project = task.getProject();
        isProjectParticipant(project, author, true);//Проверяем является ли автор участником проекта
        if (!ObjectUtils.isEmpty(task.getTaskPerformer())) {//Проверяем изменился ли исполнитель, если да, проверяем участник ли он команды
            if (!ObjectUtils.isEmpty(performerBeforeUpdate) &&
                    task.getTaskPerformer().getUid().equals(performerBeforeUpdate.getUid())) {
                task.setTaskPerformer(performerBeforeUpdate);
            } else {
                setPerformerAndSendMail(taskDTO.getTaskPerformer(), project, task);
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

    @Override
    public Task findByUid(UUID uid) {
        return taskRepository.findByUid(uid).orElseThrow(TaskDoesNotExistException::new);
    }

    @Override
    public List<TaskOutDTO> getTaskDependencies(UUID uid) {
        Task task = findByUid(uid);
        List<Task> tasks = taskRepository.findAllByTaskParent(task);

        return tasks.stream().map(mapper::entityToOutDto).toList();
    }

    @Override
    public List<TaskOutDTO> setTaskParent(UUID taskParent, TaskChildDTO childDTO) {
        Task parent = findByUid(taskParent);
        List<Task> temp = new ArrayList<>();
        for (UUID taskChild : childDTO.children) {
            Task child = findByUid(taskChild);
            if (isParentNotChild(child, parent)) {
                child.setTaskParent(parent);
                temp.add(child);
            } else {
                throw new CyclicDependencyException(resourceBundle.getString("CYCLIC_DEPENDENCY_TASK") + taskChild.toString());
            }
        }
        taskRepository.saveAll(temp);
        List<Task> children = taskRepository.findAllByTaskParent(parent);
        return children.stream().map(mapper::entityToOutDto).toList();
    }

    @Override
    public List<TaskOutDTO> deleteTaskParent(UUID taskParent, TaskChildDTO childDTO) {
        Task parent = findByUid(taskParent);
        List<Task> temp = new ArrayList<>();
        for (UUID taskChild : childDTO.children) {
            Task child = findByUid(taskChild);
            if (parent.equals(child.getTaskParent())) {
                child.setTaskParent(null);
                temp.add(child);
            } else {
                throw new DependencyDoesNotExistException(resourceBundle.getString("DEPENDENCY_DOES_NOT_EXIST") + taskChild.toString());
            }
        }
        taskRepository.saveAll(temp);
        List<Task> children = taskRepository.findAllByTaskParent(parent);
        return children.stream().map(mapper::entityToOutDto).toList();
    }

    private void setPerformerAndSendMail(UUID performer, Project project, Task task) {
        Employee taskPerformer = employeeService.findByUid(performer);
        isProjectParticipant(project, taskPerformer, false);
        task.setTaskPerformer(taskPerformer);
        EmailDTO emailDTO = creatorMailDTO.getDtoForTask(taskPerformer, task.getName(), project.getName());
        messageProducer.sendMessage(emailDTO);
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

    public boolean isParentNotChild(Task child, Task parent) {
        if (ObjectUtils.isEmpty(parent.getTaskParent()))
            return true;
        else if (child.equals(parent.getTaskParent()))
            return false;
        else
            return isParentNotChild(child, parent.getTaskParent());
    }
}
