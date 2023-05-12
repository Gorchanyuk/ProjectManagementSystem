package didgital.design.dto.task;

import didgital.design.common.enumerate.StatusTask;
import didgital.design.dto.employee.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private String name;

    private String description;

    private EmployeeDTO taskPerformer;

    private int executionTime;

    private Date deadline;

    private StatusTask status;

//    private Employee author;

//    private Date dateOfCreated;

//    private Date dateOfUpdate;

}
