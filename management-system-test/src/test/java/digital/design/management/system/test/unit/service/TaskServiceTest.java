package digital.design.management.system.test.unit.service;

import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.common.exception.AuthorIsNotInvolvedInProjectException;
import digital.design.management.system.common.exception.CanNotAssignGivenStatusException;
import digital.design.management.system.common.exception.EmployeeIsNotInvolvedInProjectException;
import digital.design.management.system.common.exception.TaskDoesNotExistException;
import digital.design.management.system.dto.task.TaskCreateDTO;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskFilterDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.Task;
import digital.design.management.system.mapping.impl.TaskMapper;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.repository.TaskRepository;
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.service.impl.TaskServiceImpl;
import digital.design.management.system.specification.TaskSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Spy
    private TaskMapper mapper;
    @Mock
    private ProjectTeamRepository projectTeamRepository;
    @Mock
    private ProjectService projectService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void isProjectParticipantShouldThrowAuthorIsNotInvolvedInProjectException() {
        TaskCreateDTO createDTO = new TaskCreateDTO();
        when((projectService.findByUid(any()))).thenReturn(new Project());
        when(projectTeamRepository.existsById(any())).thenReturn(false);

        Assertions.assertThrows(AuthorIsNotInvolvedInProjectException.class,
                () -> taskService.createTask(createDTO, new Employee()));
    }

    @Test
    public void isProjectParticipantShouldThrowEmployeeIsNotInvolvedInProjectException() {
        TaskCreateDTO createDTO = new TaskCreateDTO();
        createDTO.setTaskPerformer(UUID.randomUUID());
        when(employeeService.findByUid(any())).thenReturn(new Employee());
        when((projectService.findByUid(any()))).thenReturn(new Project());
        when(projectTeamRepository.existsById(any())).thenReturn(true).thenReturn(false);

        Assertions.assertThrows(EmployeeIsNotInvolvedInProjectException.class,
                () -> taskService.createTask(createDTO, new Employee()));
    }

    @Test
    public void createTaskShouldCreateNewTask() {
        TaskCreateDTO createDTO = new TaskCreateDTO();
        createDTO.setTaskPerformer(UUID.randomUUID());
        when((projectService.findByUid(any()))).thenReturn(new Project());
        when(employeeService.findByUid(any())).thenReturn(new Employee());
        when(projectTeamRepository.existsById(any())).thenReturn(true);

        TaskOutDTO outDTO = taskService.createTask(createDTO, new Employee());

        //projectTeamRepository.existsById(any()) вызывается в приватном методе isProjectParticipant
        verify(projectTeamRepository, times(2)).existsById(any());
        verify(taskRepository).save(any());
        Assertions.assertNotNull(outDTO);
    }

    @Test
    public void findByUidThrowTaskDoesNotExistException() {
        UUID uid = UUID.randomUUID();
        when(taskRepository.findByUid(uid)).thenReturn(Optional.empty());

        //Метод findByUid приватный, поэтому проверяем через метод taskService
        Assertions.assertThrows(TaskDoesNotExistException.class,
                () -> taskService.updateTask(uid, new TaskDTO(), new Employee()));
    }

    /*Проверяем обновление задачи. в условиях задаем что жо этого исполнитель не был указан, а теперь добавлен*/
    @Test
    public void updateTaskShouldUpadateTask() {
        UUID uid = UUID.randomUUID();
        Task task = Task.builder()
                .project(new Project())
                .build();
        TaskDTO dto = TaskDTO.builder().taskPerformer(UUID.randomUUID()).build();
        when(taskRepository.findByUid(uid)).thenReturn(Optional.of(task));
        when(projectTeamRepository.existsById(any())).thenReturn(true);
        when(employeeService.findByUid(any())).thenReturn(new Employee());

        TaskOutDTO outDTO = taskService.updateTask(uid, dto, new Employee());

        //projectTeamRepository.existsById(any()) вызывается в приватном методе isProjectParticipant
        verify(projectTeamRepository, times(2)).existsById(any());
        verify(taskRepository).save(any());
        Assertions.assertNotNull(outDTO);
    }

    @Test
    public void getTasksWithFilterShouldReturnResult() {
        TaskFilterDTO filterDTO = new TaskFilterDTO();
        when(taskRepository.findAll(
                TaskSpecification.getSpecification(filterDTO),
                Sort.by("dateOfCreated").descending()))
                .thenReturn(List.of(new Task()));

        List<TaskOutDTO> outDTOS = taskService.getTasksWithFilter(filterDTO);

        Assertions.assertNotNull(outDTOS);
    }

    @Test
    public void updateStatusTaskShouldThrowCanNotAssignGivenStatusException(){
        UUID uid = UUID.randomUUID();
        StatusTask status = StatusTask.NEW;
        Task task = Task.builder().status(StatusTask.COMPLETE).build();
        when(taskRepository.findByUid(uid)).thenReturn(Optional.of(task));

        Assertions.assertThrows(CanNotAssignGivenStatusException.class,
                ()->taskService.updateStatusTask(uid, status));
    }

    @Test
    public void updateStatusTaskShouldUpdateStatus(){
        UUID uid = UUID.randomUUID();
        StatusTask status = StatusTask.COMPLETE;
        Task task = Task.builder().status(StatusTask.WORK).build();
        when(taskRepository.findByUid(uid)).thenReturn(Optional.of(task));
        doReturn(new TaskOutDTO()).when(mapper).entityToOutDto(task);

        TaskOutDTO outDTO = taskService.updateStatusTask(uid, status);

        verify(taskRepository).save(task);
        Assertions.assertNotNull(outDTO);
        Assertions.assertEquals(status, task.getStatus());
    }

}
