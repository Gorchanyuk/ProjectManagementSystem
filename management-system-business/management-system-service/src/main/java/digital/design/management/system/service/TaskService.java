package digital.design.management.system.service;

import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.dto.task.*;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    TaskOutDTO createTask(TaskCreateDTO taskDTO, Employee author);

    TaskOutDTO updateTask(UUID uid, TaskDTO taskDTO, Employee author);

    List<TaskOutDTO> getTasksWithFilter(TaskFilterDTO taskFilterDTO);

    TaskOutDTO updateStatusTask(UUID uid, StatusTask statusTask);

    Task findByUid(UUID uid);

    List<TaskOutDTO> getTaskDependencies(UUID uid);

    List<TaskOutDTO> setTaskParent(UUID taskParent, TaskChildDTO taskChildDTO);

    List<TaskOutDTO> deleteTaskParent(UUID uid, TaskChildDTO childDto);
}
