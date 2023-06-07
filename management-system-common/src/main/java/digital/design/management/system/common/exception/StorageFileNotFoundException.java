package digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StorageFileNotFoundException extends RuntimeException{
    public StorageFileNotFoundException(String message) {
        super(message);
    }
}
