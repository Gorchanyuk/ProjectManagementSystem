package didgital.design.management.system.dto.task;

import didgital.design.management.system.dto.employee.EmployeeDTO;

import java.util.Date;

public class TaskOutDTO extends TaskDTO{

    private Long id;

    private EmployeeDTO author;

    private Date dateOfCreated;

    private Date dateOfUpdate;
}
