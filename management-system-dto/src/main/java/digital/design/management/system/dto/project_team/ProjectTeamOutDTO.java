package digital.design.management.system.dto.project_team;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeamOutDTO{

    EmployeeOutDTO employeeId;

    private RoleEmployee roleEmployee;

}
