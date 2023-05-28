package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.entity.Task;
import digital.design.management.system.mapping.Mapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class TaskMapper implements Mapper<Task, TaskDTO, TaskOutDTO> {

    @Override
    public Task dtoToEntity(TaskDTO dto) {
        log.debug("Mapping TaskDTO to Task");
        return Task.builder()
                .uid(UUID.randomUUID())
                .name(dto.getName())
                .description(dto.getDescription())
                .executionTime(dto.getExecutionTime())
                .deadline(dto.getDeadline())
                .build();
    }

    @Override
    public Task dtoToEntity(TaskDTO dto, Task task){
        log.debug("Mapping an TaskDTO to an Task in a given task");
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setExecutionTime(dto.getExecutionTime());
        task.setDeadline(dto.getDeadline());

        return task;
    }

    @Override
    public TaskOutDTO entityToOutDto(Task task) {
        log.debug("Mapping Task to TaskOutDTO");
        return TaskOutDTO.builder()
                .name(task.getName())
                .description(task.getDescription())
                .taskPerformer(task.getTaskPerformer() == null ? null : task.getTaskPerformer().getUid())
                .executionTime(task.getExecutionTime())
                .deadline(task.getDeadline())
                .project(task.getProject().getUid())
                .uid(task.getUid())
                .author(task.getAuthor().getUid())
                .status(task.getStatus())
                .dateOfCreated(task.getDateOfCreated())
                .dateOfUpdate(task.getDateOfUpdate())
                .build();
    }



}
