package didgital.design.dto.project_team;

import didgital.design.dto.employee.EmployeeIdDTO;
import didgital.design.dto.project.ProjectIdDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeamDTO {

    private ProjectIdDTO projectId;

    private EmployeeIdDTO employeeId;
}
