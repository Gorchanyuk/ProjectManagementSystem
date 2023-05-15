package didgital.design.dto.project_team;

import didgital.design.common.enumerate.RoleEmployee;
import didgital.design.dto.employee.EmployeeOutDTO;
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
