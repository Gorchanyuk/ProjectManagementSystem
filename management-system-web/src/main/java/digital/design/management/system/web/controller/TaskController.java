package digital.design.management.system.web.controller;

import digital.design.management.system.dto.task.*;
import digital.design.management.system.dto.util.InputDataErrorResponse;
import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.security.EmployeeDetails;
import digital.design.management.system.service.TaskService;
import digital.design.management.system.validator.TaskValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Создание задачи",
            description = "Создает задачу и сохраняет в БД")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
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

            log.warn("POST request on .../task contains errors: {}", infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }

        TaskOutDTO taskOutDTO = taskService.createTask(taskDTO, author.getEmployee());
        return new ResponseEntity<>(taskOutDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновление задачи",
            description = "Обновление данных задачи")
    @PutMapping(value = "{uid}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateTask(@Parameter(description = "uid задачи, которую нужно обновить")
                                             @PathVariable("uid") UUID uid,
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

            log.warn("PUT request on .../task/{} contains errors: {}", uid, infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        TaskOutDTO taskOutDTO = taskService.updateTask(uid, taskDTO, author.getEmployee());
        return new ResponseEntity<>(taskOutDTO, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Поиск задач",
            description = "Получение списка задач по условиям фильтра")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskOutDTO> getTasksWithFilter(@RequestBody TaskFilterDTO taskFilterDTO) {
        return taskService.getTasksWithFilter(taskFilterDTO);
    }

    @Operation(summary = "Повышение стаутса задачи",
            description = "Статус задачи переводится в новый статус, который поступил на вход, если этот статус " +
                    "является допустимым")
    @PutMapping(value = "/raise_status/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskOutDTO> updateStatusProject(@Parameter(description = "uid задачи, которой нужно изменить статус")
                                                          @PathVariable("uid") UUID uid,
                                                          @RequestParam("status") StatusTask statusTask) {
        TaskOutDTO taskOutDTO = taskService.updateStatusTask(uid, statusTask);
        return new ResponseEntity<>(taskOutDTO, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Получение связаных задач",
            description = "Получение задач от которых зависит выполнение заданной задачи")
    @GetMapping(value = "/dependencies/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskOutDTO> getTaskDependencies(@Parameter(description = "uid задачи, зависимости которой нужно получить")
                                                @PathVariable("uid") UUID uid) {
        return taskService.getTaskDependencies(uid);
    }

    @Operation(summary = "Связывание задач",
            description = "Устанавливает зависимости между заданной задачей и задачами от которых она будет зависеть")
    @PostMapping(value = "/dependencies/{uid}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskOutDTO> setTaskDependencies(@Parameter(description = "uid задачи, которая будет зависеть от других задач")
                                                @PathVariable("uid") UUID uid,
                                                @RequestBody TaskChildDTO childDto) {

        return taskService.setTaskParent(uid, childDto);
    }

    @Operation(summary = "Удаление связей между задачами",
            description = "Удаляет зависимости между заданной задачей и задачами от которых она зависит")
    @DeleteMapping(value = "/dependencies/{uid}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskOutDTO> deleteTaskDependencies(@Parameter(description = "uid задачи, которая зависит от других задач")
                                                   @PathVariable("uid") UUID uid,
                                                   @RequestBody TaskChildDTO childDto) {

        return taskService.deleteTaskParent(uid, childDto);
    }

}
