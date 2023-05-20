package digital.design.management.system.dto.project_team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeamDeleteDTO {

    private UUID projectUid;

    private UUID employeeUid;
}
