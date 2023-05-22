package digital.design.management.system.dto.project_team;

import digital.design.management.system.enumerate.RoleEmployee;
import digital.design.management.system.enumerate.StatusProject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Модель для добавления сотрудника в проект с присвоением роли")
public class ProjectTeamDTO {

    @Schema(description = "Уникальный идентификатор проекта uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID projectUid;

    @Schema(description = "Уникальный идентификатор сотрудника uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID employeeUid;

    @NotNull(message = "Роль сотрудника должна быть представлена одним из следующих вариантов " +
            "PROJECT_MANAGER, ANALYST, DEVELOPER или TESTER")
    @Schema(description = "Роль сотрудника", example = "DEVELOPER", oneOf = RoleEmployee.class)
    private RoleEmployee roleEmployee;


}
