package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.*;
import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.dto.task.TaskCreateDTO;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskFilterDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.enumerate.StatusTask;
import digital.design.management.system.security.EmployeeDetails;
import digital.design.management.system.service.TaskService;
import digital.design.management.system.validator.TaskValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Tag(name = "Задачи", description = "Контроллер для управления задачами")
@Log4j2
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
        log.debug("POST request on .../task, params: taskDTO={}", taskDTO);
        taskValidator.validate(taskDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            log.warn("POST request on .../task contains errors: {}", infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }

        TaskOutDTO taskOutDTO = taskService.createTask(taskDTO, author);
        log.debug("POST request on .../task is complete");
        return new ResponseEntity<>(taskOutDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "{uid}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Изменение задачи")
    public ResponseEntity<Object> updateTask(@PathVariable("uid") UUID uid,
                                             @AuthenticationPrincipal EmployeeDetails author,
                                             @Valid @RequestBody TaskDTO taskDTO,
                                             BindingResult bindingResult) {
        log.debug("PUT request on .../task/{}", uid);
        taskValidator.validate(taskDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            log.warn("PUT request on .../task/{} contains errors: {}", uid, infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        TaskOutDTO taskOutDTO = taskService.updateTask(uid, taskDTO, author);
        log.debug("PUT request on .../task is complete");
        return new ResponseEntity<>(taskOutDTO, HttpStatus.ACCEPTED);
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Получение списка задач по условиям фильтра")
//    public List<TaskOutDTO> getTasksWithFilter(@RequestBody TaskFilterDTO taskFilterDTO){
//
//        return taskService.getTasksWithFilter(taskFilterDTO);
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение списка задач по условиям фильтра")
    public List<TaskOutDTO> getTasksWithFilter(@RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "status", required = false) List<StatusTask> status,
                                               @RequestParam(value = "author", required = false) UUID author,
                                               @RequestParam(value = "taskPerformer", required = false) UUID taskPerformer,
                                               @RequestParam(value = "deadlineStart", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate deadlineStart,
                                               @RequestParam(value = "deadlineEnd", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate deadlineEnd,
                                               @RequestParam(value = "dateOfCreatedStart", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateOfCreatedStart,
                                               @RequestParam(value = "dateOfCreatedEnd", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateOfCreatedEnd) {

        TaskFilterDTO filter = new TaskFilterDTO(name, status, author, taskPerformer, deadlineStart, deadlineEnd, dateOfCreatedStart, dateOfCreatedEnd);
        log.debug("GET request on .../task, params: filter={}", filter);
        return taskService.getTasksWithFilter(filter);
    }

    @PutMapping(value = "/raise_status/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Повышение стаутса задачи")
    public TaskOutDTO updateStatusProject(@PathVariable("uid") UUID uid,
                                          @RequestParam("status") StatusTask statusTask) {
        log.debug("PUT request on .../task/raise_status/{}", uid);
        return taskService.updateStatusTask(uid, statusTask);
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
    private ResponseEntity<InputDataErrorResponse> handleException(HttpMessageNotReadableException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "unknown",
                e.getMessage()
        );
        log.warn("Error in the format of the transmitted data");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeIsNotInvolvedInProjectException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT_1")
        );
        log.warn(resourceBundle.getString("EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT_1"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

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
    private ResponseEntity<InputDataErrorResponse> handleException(ProjectDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("PROJECT_DOES_NOT_EXIST")
        );
        log.warn(resourceBundle.getString("PROJECT_DOES_NOT_EXIST"));
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
    private ResponseEntity<InputDataErrorResponse> handleException(CanNotAssignGivenTaskStatusException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "status",
                resourceBundle.getString("CANNOT_ASSIGN_GIVEN_TASK_STATUS")
        );
        log.warn(resourceBundle.getString("CANNOT_ASSIGN_GIVEN_TASK_STATUS"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(MaximumTaskStatusException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "status",
                resourceBundle.getString("MAXIMUM_TASK_STATUS_EXCEPTION")
        );
        log.warn(resourceBundle.getString("MAXIMUM_TASK_STATUS_EXCEPTION"));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
