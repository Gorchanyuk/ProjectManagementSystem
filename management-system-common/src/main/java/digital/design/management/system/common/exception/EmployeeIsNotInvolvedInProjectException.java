package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeIsNotInvolvedInProjectException extends RuntimeException{
    public EmployeeIsNotInvolvedInProjectException(String message) {
        super(message);
    }
}
