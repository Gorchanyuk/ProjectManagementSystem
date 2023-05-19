package digital.design.management.system.dto.project_team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeamDeleteDTO {

    private Long projectId;

    private Long employeeId;
}
