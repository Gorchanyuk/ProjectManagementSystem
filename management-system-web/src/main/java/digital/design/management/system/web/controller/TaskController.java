package digital.design.management.system.web.controller;

import digital.design.management.system.dto.util.InputDataErrorResponse;
import digital.design.management.system.dto.task.TaskCreateDTO;
import digital.design.management.system.dto.task.TaskDTO;
import digital.design.management.system.dto.task.TaskFilterDTO;
import digital.design.management.system.dto.task.TaskOutDTO;
import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.security.EmployeeDetails;
import digital.design.management.system.service.TaskService;
import digital.design.management.system.validator.TaskValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Tag(name = "Задачи", description = "Контроллер для управления задачами")
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private final TaskValidator taskValidator;

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

        TaskOutDTO taskOutDTO = taskService.createTask(taskDTO, author.getEmployee());
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
        TaskOutDTO taskOutDTO = taskService.updateTask(uid, taskDTO, author.getEmployee());
        log.debug("PUT request on .../task is complete");
        return new ResponseEntity<>(taskOutDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение списка задач по условиям фильтра")
    public List<TaskOutDTO> getTasksWithFilter(@RequestBody TaskFilterDTO taskFilterDTO){
        log.debug("GET request on .../task, params: filter={}", taskFilterDTO);
        return taskService.getTasksWithFilter(taskFilterDTO);
    }

    @PutMapping(value = "/raise_status/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Повышение стаутса задачи")
    public TaskOutDTO updateStatusProject(@PathVariable("uid") UUID uid,
                                          @RequestParam("status") StatusTask statusTask) {
        log.debug("PUT request on .../task/raise_status/{}", uid);
        return taskService.updateStatusTask(uid, statusTask);
    }

}
