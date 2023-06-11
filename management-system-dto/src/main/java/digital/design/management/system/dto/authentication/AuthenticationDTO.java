package digital.design.management.system.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Модель для аутентификации в системе")
public class AuthenticationDTO {

    @Schema(description = "Учетная запись или электронная почта", example = "superuser")
    private String username;

    @Schema(description = "Пароль", example = "password")
    private String password;
}
