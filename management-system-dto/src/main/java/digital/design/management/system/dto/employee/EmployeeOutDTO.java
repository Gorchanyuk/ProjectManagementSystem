package digital.design.management.system.dto.employee;

import digital.design.management.system.common.enumerate.StatusEmployee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель сотрудника для передачи данных на внешний интерфейс")
public class EmployeeOutDTO {

    @Schema(description = "Уникальный идентификатор uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID uid;

    @Schema(description = "Статус", example = "ACTIV", oneOf = StatusEmployee.class)
    private StatusEmployee status;

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @Schema(description = "Отчество", example = "Иванович")
    private String surname;

    @Schema(description = "Должность", example = "Разработчик")
    private String jobTitle;

    @Schema(description = "Учетная запись", example = "ivan_123")
    private String username;

    @Schema(description = "Почта", example = "ivanov@mail.com")
    private String email;

}