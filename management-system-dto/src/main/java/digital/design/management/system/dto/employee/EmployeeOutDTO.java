package digital.design.management.system.dto.employee;

import digital.design.management.system.enumerate.StatusEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeOutDTO extends EmployeeDTO {

    private String uid;

    private StatusEmployee status;

}