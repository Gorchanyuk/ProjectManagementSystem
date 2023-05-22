package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.EmployeeAlreadyParticipatingInProjectException;
import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.dto.project_team.ProjectTeamDTO;
import digital.design.management.system.dto.project_team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.project_team.ProjectTeamOutDTO;
import digital.design.management.system.service.ProjectTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.java.digital.design.management.system.common.exception.EmployeeDoesNotExistException;
import main.java.digital.design.management.system.common.exception.ProjectDoesNotExistException;
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
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;
    private final ResourceBundle resourceBundle;

    @GetMapping(value = "/{project_uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение всех сотрудников из заданного проекта по его uid")
    public List<ProjectTeamOutDTO> getAllParty(@PathVariable("project_uid") UUID projectUid) {
        return projectTeamService.getAllParty(projectUid);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавление сотрудника в команду")
    public ResponseEntity<Object> addParticipant(@Valid @RequestBody ProjectTeamDTO team,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField()).build())
                    .toList();

            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        ProjectTeamOutDTO projectTeamOutDTO = projectTeamService.addParticipant(team);

        return new ResponseEntity<>(projectTeamOutDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаление сотрудника из команды")
    public ResponseEntity<Object> deleteParticipant(@RequestBody ProjectTeamDeleteDTO projectTeamDeleteDTO) {
        projectTeamService.deleteParticipant(projectTeamDeleteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeAlreadyParticipatingInProjectException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "employeeUid",
                resourceBundle.getString("EMPLOYEE_ALREADY_PARTICIPATING_IN_PROJECT")
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "employeeUid",
                resourceBundle.getString("EMPLOYEE_DOES_NOT_EXIST")
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(ProjectDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "projectUid",
                resourceBundle.getString("PROJECT_DOES_NOT_EXIST")
        );
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
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
