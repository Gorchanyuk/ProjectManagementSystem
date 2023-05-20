package digital.design.management.system.dto.project_team;

import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.enumerate.RoleEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeamOutDTO {

    private EmployeeOutDTO employeeId;

    private RoleEmployee roleEmployee;
}
