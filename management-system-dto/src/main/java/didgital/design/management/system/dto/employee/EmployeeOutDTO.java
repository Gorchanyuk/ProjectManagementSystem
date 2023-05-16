package didgital.design.management.system.dto.employee;

import didgital.design.management.system.common.enumerate.StatusEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeOutDTO extends EmployeeDTO {

    private Long id;

    private StatusEmployee status;

}