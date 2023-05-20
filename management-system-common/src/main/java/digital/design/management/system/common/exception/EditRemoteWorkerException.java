package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EditRemoteWorkerException extends RuntimeException{
    public EditRemoteWorkerException(String message){
        super(message);
    }
}
