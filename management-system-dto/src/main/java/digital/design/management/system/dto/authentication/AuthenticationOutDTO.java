package digital.design.management.system.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Модель для передачи клиенту JWT токена для аутентификации")
public class AuthenticationOutDTO {

    @Schema(description = "JWT токен для аутентификации")
    public String jwtToken;
}
