package digital.design.management.system.service;

import digital.design.management.system.dto.authentication.AuthenticationDTO;
import digital.design.management.system.dto.authentication.AuthenticationOutDTO;

public interface AuthenticationService {

    AuthenticationOutDTO authentication(AuthenticationDTO authenticationDTO);
}
