package digital.design.management.system.dto.employee;

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
public class EmployeeDTO {

    @NotBlank(message = "Поле 'Фамилия' не может быть пустым")
    private String lastName;

    @NotBlank(message = "Поле 'Имя' не может быть пустым")
    private String firstName;

    private String surname;

    private String jodTitle;

    private String username;

    @Email
    private String email;

}