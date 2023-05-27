package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class SuchUsernameAlreadyExistException extends RuntimeException {

    public SuchUsernameAlreadyExistException(String message) {
        super(message);
    }
}
