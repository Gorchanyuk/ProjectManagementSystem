package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StorageSaveFileException extends RuntimeException{
    public StorageSaveFileException(String message) {
        super(message);
    }
}
