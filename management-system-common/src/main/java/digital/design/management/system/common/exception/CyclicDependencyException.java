package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CyclicDependencyException extends RuntimeException{
    public CyclicDependencyException(String message) {
        super(message);
    }
}
