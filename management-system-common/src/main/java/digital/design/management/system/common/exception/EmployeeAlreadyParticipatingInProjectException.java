package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeAlreadyParticipatingInProjectException extends RuntimeException{
    public EmployeeAlreadyParticipatingInProjectException(String message){
        super(message);
    }
}
