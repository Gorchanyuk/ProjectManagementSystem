package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaskDoesNotExistException extends RuntimeException{
    public TaskDoesNotExistException(String message) {
        super(message);
    }
}
