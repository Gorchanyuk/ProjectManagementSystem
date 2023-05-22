package digital.design.management.system.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель сотрудника для передачи данных на сервер")
public class EmployeeDTO {

    @NotBlank(message = "Поле 'Фамилия' не может быть пустым")
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @NotBlank(message = "Поле 'Имя' не может быть пустым")
    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @Schema(description = "Отчество", example = "Иванович")
    private String surname;

    @Schema(description = "Должность", example = "Разработчик")
    private String jodTitle;

    @Schema(description = "Учетная запись", example = "ivan_123")
    private String username;

    @Email
    @Schema(description = "Почта", example = "ivanov@mail.com")
    private String email;

}