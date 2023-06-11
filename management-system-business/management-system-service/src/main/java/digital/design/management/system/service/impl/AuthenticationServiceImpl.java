package digital.design.management.system.service.impl;

import digital.design.management.system.dto.authentication.AuthenticationDTO;
import digital.design.management.system.dto.authentication.AuthenticationOutDTO;
import digital.design.management.system.security.JWTUtil;
import digital.design.management.system.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationOutDTO authentication(AuthenticationDTO authenticationDTO){

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(), authenticationDTO.getPassword());

        //Если аутентификация не пройдет, будет выброшено исключение BadCredentialsException или DisabledException,
        //обрабатываю в ExceptionHandlerController
        authenticationManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        log.info("Employee {} authenticated", authenticationDTO.getUsername());
        return AuthenticationOutDTO.builder().jwtToken(token).build();
    }
}
