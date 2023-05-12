package didgital.design.dto.employee;

import didgital.design.common.enumerate.StatusEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeOutDTO extends EmployeeDTO {

    private long id;

    private StatusEmployee status;

}