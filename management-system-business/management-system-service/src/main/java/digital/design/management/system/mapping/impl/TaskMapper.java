package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.entity.Task;
import digital.design.management.system.mapping.Mapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskMapper implements Mapper<Task, TaskDTO, TaskOutDTO> {

    @Override
    public Task dtoToEntity(TaskDTO dto) {

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

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setExecutionTime(dto.getExecutionTime());
        task.setDeadline(dto.getDeadline());

        return task;
    }

    @Override
    public TaskOutDTO entityToOutDto(Task task) {

        return TaskOutDTO.builder()
                .name(task.getName())
                .description(task.getDescription())
                .taskPerformer(task.getTaskPerformer().getUid())
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
