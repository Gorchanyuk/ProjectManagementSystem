package digital.design.management.system.service;

import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.dto.task.TaskCreateDTO;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskFilterDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.security.EmployeeDetails;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    TaskOutDTO createTask(TaskCreateDTO taskDTO, EmployeeDetails author);

    TaskOutDTO updateTask(UUID uid, TaskDTO taskDTO, EmployeeDetails author);

    List<TaskOutDTO> getTasksWithFilter(TaskFilterDTO taskFilterDTO);

    TaskOutDTO updateStatusTask(UUID uid, StatusTask statusTask);

}
