package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileWithThisHashcodeAlreadyExistsException extends RuntimeException{

    public FileWithThisHashcodeAlreadyExistsException(String message) {
        super(message);
    }
}
