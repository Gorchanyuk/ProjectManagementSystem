package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StatusProjectHasNotNextStatusException extends RuntimeException {

    public StatusProjectHasNotNextStatusException(String message) {
        super(message);
    }
}
