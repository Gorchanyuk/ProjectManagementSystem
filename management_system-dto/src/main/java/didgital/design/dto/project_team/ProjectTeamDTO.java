package didgital.design.dto.project_team;

import didgital.design.common.enumerate.RoleEmployee;
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
