package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CanNotAssignGivenStatusException extends RuntimeException{

    public CanNotAssignGivenStatusException(String message) {
        super(message);
    }
}
