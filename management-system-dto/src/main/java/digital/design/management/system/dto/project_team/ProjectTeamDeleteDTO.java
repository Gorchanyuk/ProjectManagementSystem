package digital.design.management.system.dto.project_team;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель для удаления сотрудника из проекта")
public class ProjectTeamDeleteDTO {

    @Schema(description = "Уникальный идентификатор проекта uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID projectUid;

    @Schema(description = "Уникальный идентификатор сотрудника uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID employeeUid;
}
