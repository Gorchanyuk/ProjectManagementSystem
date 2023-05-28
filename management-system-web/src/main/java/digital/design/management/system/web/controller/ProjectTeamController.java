package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.EmployeeAlreadyParticipatingInProjectException;
import digital.design.management.system.common.exception.EmployeeIsNotInvolvedInProjectException;
import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.dto.project_team.ProjectTeamDTO;
import digital.design.management.system.dto.project_team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.project_team.ProjectTeamOutDTO;
import digital.design.management.system.service.ProjectTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import digital.design.management.system.common.exception.EmployeeDoesNotExistException;
import digital.design.management.system.common.exception.ProjectDoesNotExistException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project_team")
@Tag(name = "Команды проектов", description = "Контроллер для управления командами проектов")
@Log4j2
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;
    private final ResourceBundle resourceBundle;

    @GetMapping(value = "/{project_uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение всех сотрудников из заданного проекта по его uid")
    public List<ProjectTeamOutDTO> getAllParty(@PathVariable("project_uid") UUID projectUid) {
        log.debug("GET request on .../project_team/{}", projectUid);
        return projectTeamService.getAllParty(projectUid);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавление сотрудника в команду")
    public ResponseEntity<Object> addParticipant(@Valid @RequestBody ProjectTeamDTO team,
                                                 BindingResult bindingResult) {
        log.debug("Post request on .../project_team");
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField()).build())
                    .toList();

            log.warn("POST request on .../project_team contains errors: {}", infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        ProjectTeamOutDTO projectTeamOutDTO = projectTeamService.addParticipant(team);
        log.debug("POST request on .../project_team is complete");
        return new ResponseEntity<>(projectTeamOutDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаление сотрудника из команды")
    public ResponseEntity<Object> deleteParticipant(@RequestBody ProjectTeamDeleteDTO projectTeamDeleteDTO) {
        log.debug("DELETE request on .../project_team");
        projectTeamService.deleteParticipant(projectTeamDeleteDTO);
        log.debug("DELETE request on .../project_team is complete");
        return new ResponseEntity<>(HttpStatus.OK);
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
                resourceBundle.getString("EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT_2")
        );
        log.warn(resourceBundle.getString("EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT_2"));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "employeeUid",
                resourceBundle.getString("EMPLOYEE_DOES_NOT_EXIST")
        );
        log.warn(resourceBundle.getString("EMPLOYEE_DOES_NOT_EXIST"));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(ProjectDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "projectUid",
                resourceBundle.getString("PROJECT_DOES_NOT_EXIST")
        );
        log.warn(resourceBundle.getString("PROJECT_DOES_NOT_EXIST"));
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /* HttpMessageNotReadableException - это исключение сообщает что в качестве аргумента
        поступил несовместимый тип, так как ошибка может быть в любом поле возвращаем базовое сообщение */
    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(HttpMessageNotReadableException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "unknown",
                e.getMessage()
        );
        log.warn("Error in the format of the transmitted data");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
