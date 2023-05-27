package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorIsNotInvolvedInProjectException extends RuntimeException {
    public AuthorIsNotInvolvedInProjectException(String message) {
        super(message);
    }
}
