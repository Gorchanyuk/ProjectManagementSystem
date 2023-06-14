package digital.design.management.system.test.integrations.service;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.dto.task.TaskCreateDTO;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskFilterDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.entity.Task;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.repository.TaskRepository;
import digital.design.management.system.service.TaskService;
import digital.design.management.system.test.integrations.BaseTest;
import digital.design.management.system.test.util.GenerateEmployee;
import digital.design.management.system.test.util.GenerateProject;
import digital.design.management.system.test.util.GenerateTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Rollback
@Transactional
@SpringBootTest
public class TaskServiceIT extends BaseTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private GenerateProject generateProject;
    @Autowired
    private GenerateEmployee generateEmployee;
    @Autowired
    private GenerateTask generateTask;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectTeamRepository projectTeamRepository;
    @Autowired
    private Mapper<Task, TaskDTO, TaskOutDTO> mapper;

    @Test
    public void createTaskShouldCreateNewTask() {
        Project project = generateProject.addProjects(1).get(0);
        Employee author = generateEmployee.addEmployees(1).get(0);
        Employee performer = generateEmployee.addEmployees(1).get(0);
        ProjectTeam projectTeamAuthor = new ProjectTeam(project, author, RoleEmployee.DEVELOPER);
        ProjectTeam projectTeamPerformer = new ProjectTeam(project, performer, RoleEmployee.DEVELOPER);
        projectTeamRepository.saveAll(List.of(projectTeamAuthor, projectTeamPerformer));

        TaskCreateDTO createDTO = new TaskCreateDTO();
        createDTO.setName("name");
        createDTO.setTaskPerformer(performer.getUid());
        createDTO.setExecutionTime(10);
        createDTO.setDeadline(LocalDate.now().plusDays(2));
        createDTO.setProject(project.getUid());

        taskService.createTask(createDTO, author);
        List<Task> tasks = taskRepository.findAll();

        Assertions.assertFalse(tasks.isEmpty());
    }

    @Test
    public void updateTaskShouldUpdateTask(){
        Task task = generateTask.addTasks(1).get(0);
        TaskOutDTO outDTO = mapper.entityToOutDto(task);
        UUID uid = task.getUid();
        Employee author = task.getAuthor();
        TaskDTO dto = TaskDTO.builder()
                .name("newName")
                .deadline(LocalDate.now().plusDays(10))
                .build();

        taskService.updateTask(uid, dto, author);
        Optional<Task> taskUpdate = taskRepository.findById(task.getId());
        Assertions.assertTrue(taskUpdate.isPresent());

        TaskOutDTO outDTOUpdate = mapper.entityToOutDto(taskUpdate.get());

        Assertions.assertNotEquals(outDTO, outDTOUpdate);
    }

    @Test
    public void getTasksWithFilterShouldGetTasks(){
        List<Task> tasks = generateTask.addTasks(10);
        Task task1 = tasks.get(0);
        Task task2 = tasks.get(1);
        task1.setName("develop frontend");
        task2.setName("develop backend");
        taskRepository.saveAll(List.of(task1, task2));
        TaskFilterDTO filter = TaskFilterDTO.builder()
                .name("dev")
                .build();
        List<TaskOutDTO> taskOutDTOS = taskService.getTasksWithFilter(filter);

        Assertions.assertEquals(2, taskOutDTOS.size());
    }

    @Test
    public void updateStatusTaskShouldUpdateStatus(){
        Task task = generateTask.addTasks(1).get(0);
        UUID uid = task.getUid();
        StatusTask statusUpdate = task.getStatus().getNextStatus();
        taskService.updateStatusTask(uid, statusUpdate);
        Task taskUpdate = taskRepository.findByUid(uid).get();

        Assertions.assertEquals(statusUpdate, taskUpdate.getStatus());
    }

    @Test
    public void findByUidShouldGetTask(){
        List<Task> tasks = generateTask.addTasks(10);
        UUID uid = tasks.get(0).getUid();

        Task task = taskService.findByUid(uid);

        Assertions.assertEquals(uid, task.getUid());
    }
}
