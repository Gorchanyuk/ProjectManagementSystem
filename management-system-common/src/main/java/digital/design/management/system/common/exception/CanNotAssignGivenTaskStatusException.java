package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotAssignGivenTaskStatusException extends RuntimeException{

    public CanNotAssignGivenTaskStatusException(String message) {
        super(message);
    }
}
