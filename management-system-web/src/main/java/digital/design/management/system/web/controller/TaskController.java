package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.*;
import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.dto.task.TaskCreateDTO;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.security.EmployeeDetails;
import digital.design.management.system.service.TaskService;
import digital.design.management.system.validator.TaskValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Tag(name = "Задачи", description = "Контроллер для управления задачами")
public class TaskController {

    private final TaskService taskService;
    private final TaskValidator taskValidator;
    private final ResourceBundle resourceBundle;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Создание новой задачи")
    public ResponseEntity<Object> createTask(@AuthenticationPrincipal EmployeeDetails author,
                                             @Valid @RequestBody TaskCreateDTO taskDTO,
                                             BindingResult bindingResult) {
        taskValidator.validate(taskDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }

        TaskOutDTO taskOutDTO = taskService.createTask(taskDTO, author);
        return new ResponseEntity<>(taskOutDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "{uid}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Изменение задачи")
    public ResponseEntity<Object> updateTask(@PathVariable("uid") UUID uid,
                                             @AuthenticationPrincipal EmployeeDetails author,
                                             @Valid @RequestBody TaskDTO taskDTO,
                                             BindingResult bindingResult) {
        taskValidator.validate(taskDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        TaskOutDTO taskOutDTO = taskService.updateTask(uid, taskDTO, author);
        return new ResponseEntity<>(taskOutDTO, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(AuthorIsNotInvolvedInProjectException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("AUTHOR_IS_NOT_INVOLVED_IN_PROJECT")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeIsNotInvolvedInProjectException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("EMPLOYEE_DOES_NOT_EXIST")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(ProjectDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("PROJECT_DOES_NOT_EXIST")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(TaskDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("TASK_DOES_NOT_EXIST")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
