package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.*;
import digital.design.management.system.dto.util.InputDataErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ResourceBundle;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    private final ResourceBundle resourceBundle;

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("EMPLOYEE_DOES_NOT_EXIST")
        );
        log.warn(resourceBundle.getString("EMPLOYEE_DOES_NOT_EXIST"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(SuchUsernameAlreadyExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "username",
                resourceBundle.getString("SUCH_USERNAME_ALREADY_EXIST")
        );
        log.warn(resourceBundle.getString("SUCH_USERNAME_ALREADY_EXIST"));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(HttpMessageNotReadableException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "unknown",
                e.getMessage()
        );
        log.warn("Error in the format of the transmitted data");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /* MethodArgumentTypeMismatchException - это исключение в данном случае сообщает что в качестве аргумента
    поступил несовместимый тип, поэтому возвращаем сообщение с перечислением допустимых значений */
    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(MethodArgumentTypeMismatchException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "status",
                resourceBundle.getString("STATUS_PROJECT_EXCEPTION")
        );
        log.warn("Invalid input with MethodArgumentTypeMismatchException - {}", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(SuchCodeProjectAlreadyExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "code",
                resourceBundle.getString("SUCH_CODE_PROJECT_ALREADY_EXIST")
        );
        log.warn(resourceBundle.getString("SUCH_CODE_PROJECT_ALREADY_EXIST"));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(ProjectDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("PROJECT_DOES_NOT_EXIST")
        );
        log.warn(resourceBundle.getString("PROJECT_DOES_NOT_EXIST"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeAlreadyParticipatingInProjectException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "employeeUid",
                resourceBundle.getString("EMPLOYEE_ALREADY_PARTICIPATING_IN_PROJECT")
        );
        log.warn(resourceBundle.getString("EMPLOYEE_ALREADY_PARTICIPATING_IN_PROJECT"));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeIsNotInvolvedInProjectException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "unknown",
                resourceBundle.getString("EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT")
        );
        log.warn(resourceBundle.getString("EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT"));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(AuthorIsNotInvolvedInProjectException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("AUTHOR_IS_NOT_INVOLVED_IN_PROJECT")
        );
        log.warn(resourceBundle.getString("AUTHOR_IS_NOT_INVOLVED_IN_PROJECT"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(TaskDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("TASK_DOES_NOT_EXIST")
        );
        log.warn(resourceBundle.getString("TASK_DOES_NOT_EXIST"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(CanNotAssignGivenStatusException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "status",
                resourceBundle.getString("CANNOT_ASSIGN_GIVEN_STATUS")
        );
        log.warn(resourceBundle.getString("CANNOT_ASSIGN_GIVEN_STATUS"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
