package digital.design.management.system.dto.team;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель сотрудника в проекте для передачи данных на внешний интерфейс")
public class ProjectTeamOutDTO {

    @Schema(description = "Модель сотрудника для передачи данных на внешний интерфейс")
    private EmployeeOutDTO employee;

    @Schema(description = "Роль сотрудника", example = "DEVELOPER", oneOf = RoleEmployee.class)
    private RoleEmployee roleEmployee;
}
