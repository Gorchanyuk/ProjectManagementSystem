package digital.design.management.system.dto.employee;

import digital.design.management.system.enumerate.StatusEmployee;
import digital.design.management.system.dto.employee.EmployeeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель сотрудника для передачи данных на внешний интерфейс")
public class EmployeeOutDTO extends EmployeeDTO {

    @Schema(description = "Уникальный идентификатор uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private String uid;

    @Schema(description = "Статус", example = "ACTIV", oneOf = StatusEmployee.class)
    private StatusEmployee status;

}