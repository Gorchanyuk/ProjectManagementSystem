package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityDoesNotExistException extends RuntimeException{

    public EntityDoesNotExistException(String message){
        super(message);
    }
}
