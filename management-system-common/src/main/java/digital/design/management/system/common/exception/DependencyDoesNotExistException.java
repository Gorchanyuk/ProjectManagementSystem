package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DependencyDoesNotExistException extends RuntimeException{
    public DependencyDoesNotExistException(String message) {
        super(message);
    }
}
