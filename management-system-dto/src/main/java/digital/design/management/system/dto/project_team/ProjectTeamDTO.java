package digital.design.management.system.dto.project_team;

import digital.design.management.system.common.enumerate.RoleEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeamDTO {

    private Long projectId;

    private Long employeeId;

    private RoleEmployee roleEmployee;
}
