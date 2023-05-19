package digital.design.management.system.dto.task;

import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.dto.employee.EmployeeDTO;
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

    private Integer executionTime;

    private Date deadline;

    private StatusTask status;

//    private Employee author;

//    private Date dateOfCreated;

//    private Date dateOfUpdate;

}
