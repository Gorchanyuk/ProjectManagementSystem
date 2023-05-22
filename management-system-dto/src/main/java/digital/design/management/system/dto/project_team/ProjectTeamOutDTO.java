package digital.design.management.system.dto.project_team;

import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.enumerate.RoleEmployee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель сотрудника в проекте для передачи данных на внешний интерфейс")
public class ProjectTeamOutDTO {

    @Schema(description = "Модель сотрудника для передачи данных на внешний интерфейс")
    private EmployeeOutDTO employeeId;

    @Schema(description = "Роль сотрудника", example = "DEVELOPER", oneOf = RoleEmployee.class)
    private RoleEmployee roleEmployee;
}
