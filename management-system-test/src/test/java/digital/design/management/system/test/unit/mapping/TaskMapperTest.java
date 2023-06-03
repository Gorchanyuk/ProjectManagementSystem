package digital.design.management.system.test.unit.mapping;

import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.Task;
import digital.design.management.system.mapping.impl.TaskMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class TaskMapperTest {

    private static TaskMapper mapper;

    @BeforeAll
    public static void init(){
        mapper = new TaskMapper();
    }

    @Test
    public void shouldReturnNewTask(){
        Task task = mapper.dtoToEntity(getTaskDto());

        Assertions.assertNotNull(task.getName());
        Assertions.assertNotNull(task.getDescription());
        Assertions.assertNotNull(task.getTaskPerformer());
        Assertions.assertNotNull(task.getExecutionTime());
        Assertions.assertNotNull(task.getDeadline());
    }

    @Test
    public void shouldReturnUpdatedTask(){
        Task task = getTask();
        Task taskUpdated = mapper.dtoToEntity(getTaskDto(), getTask());

        Assertions.assertNotEquals(task, taskUpdated);
    }

    @Test
    public void shouldReturnTaskOutDto(){
        TaskOutDTO outDTO = mapper.entityToOutDto(getTask());

        Assertions.assertNotNull(outDTO.getUid());
        Assertions.assertNotNull(outDTO.getName());
        Assertions.assertNotNull(outDTO.getDescription());
        Assertions.assertNotNull(outDTO.getTaskPerformer());
        Assertions.assertNotNull(outDTO.getExecutionTime());
        Assertions.assertNotNull(outDTO.getDeadline());
        Assertions.assertNotNull(outDTO.getProject());
        Assertions.assertNotNull(outDTO.getAuthor());
        Assertions.assertNotNull(outDTO.getStatus());
        Assertions.assertNotNull(outDTO.getDateOfCreated());
        Assertions.assertNotNull(outDTO.getDateOfUpdate());
    }

    @Test
    public void shouldReturnTaskDto(){
        TaskDTO dto = mapper.entityToDto(getTask());

        Assertions.assertNotNull(dto.getName());
        Assertions.assertNotNull(dto.getDescription());
        Assertions.assertNotNull(dto.getTaskPerformer());
        Assertions.assertNotNull(dto.getExecutionTime());
        Assertions.assertNotNull(dto.getDeadline());
    }

    private TaskDTO getTaskDto(){
        return TaskDTO.builder()
                .name("someNewName")
                .description("someNewDescription")
                .taskPerformer(UUID.randomUUID())
                .executionTime(20)
                .deadline(LocalDate.now().plusDays(4))
                .build();
    }

    private Task getTask(){
        return Task.builder()
                .id(1L)
                .uid(UUID.randomUUID())
                .name("name")
                .description("description")
                .taskPerformer(Employee.builder().uid(UUID.randomUUID()).build())
                .executionTime(10)
                .deadline(LocalDate.now().plusDays(3))
                .status(StatusTask.WORK)
                .author(Employee.builder().uid(UUID.randomUUID()).build())
                .dateOfCreated(LocalDate.now().minusDays(10))
                .dateOfUpdate(LocalDate.now())
                .project(Project.builder().uid(UUID.randomUUID()).build())
                .build();
    }
}
