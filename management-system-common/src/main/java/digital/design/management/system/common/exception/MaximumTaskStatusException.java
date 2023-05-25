package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MaximumTaskStatusException extends RuntimeException{

    public MaximumTaskStatusException(String message) {
        super(message);
    }
}
