package didgital.design.dto.task;

import didgital.design.dto.employee.EmployeeDTO;

import java.util.Date;

public class TaskOutDTO extends TaskDTO{

    private long id;

    private EmployeeDTO author;

    private Date dateOfCreated;

    private Date dateOfUpdate;
}
