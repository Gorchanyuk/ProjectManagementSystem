package digital.design.management.system.web.controller;

import digital.design.management.system.dto.authentication.AuthenticationDTO;
import digital.design.management.system.dto.authentication.AuthenticationOutDTO;
import digital.design.management.system.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Контроллер для аутентификации в системе")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Аутентификация",
            description = "Возвращает JWT токен для аутентификации в системе")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationOutDTO auth(@RequestBody AuthenticationDTO authenticationDTO) {
        return authenticationService.authentication(authenticationDTO);
    }
}
