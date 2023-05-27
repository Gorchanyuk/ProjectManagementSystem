package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProjectDoesNotExistException extends RuntimeException {

    public ProjectDoesNotExistException(String message) {
        super(message);
    }
}
