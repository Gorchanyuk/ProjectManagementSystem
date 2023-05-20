package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SuchCodeProjectAlreadyExistException extends RuntimeException{

    public SuchCodeProjectAlreadyExistException(String message){
        super(message);
    }
}
