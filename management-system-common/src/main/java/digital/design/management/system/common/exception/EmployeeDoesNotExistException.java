package main.java.digital.design.management.system.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeDoesNotExistException extends RuntimeException {

    public EmployeeDoesNotExistException(String message) {
        super(message);
    }
}
