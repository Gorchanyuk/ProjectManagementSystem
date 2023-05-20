package digital.design.management.system.dto.project_team;

import digital.design.management.system.enumerate.RoleEmployee;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeamDTO {

    private UUID projectUid;

    private UUID employeeUid;

    @NotNull(message = "Роль сотрудника должна быть представлена одним из следующих вариантов " +
            "PROJECT_MANAGER, ANALYST, DEVELOPER или TESTER")
    private RoleEmployee roleEmployee;


}
