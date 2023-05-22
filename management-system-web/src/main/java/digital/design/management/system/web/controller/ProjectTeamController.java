package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.EmployeeAlreadyParticipatingInProjectException;
import digital.design.management.system.common.exception.EntityDoesNotExistException;
import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.dto.project_team.ProjectTeamDTO;
import digital.design.management.system.dto.project_team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.project_team.ProjectTeamOutDTO;
import digital.design.management.system.service.ProjectTeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;
    private final ResourceBundle resourceBundle;

    @GetMapping("/{project_uid}")
    public List<ProjectTeamOutDTO> getAllParty(@PathVariable("project_uid") UUID projectUid) {
        return projectTeamService.getAllParty(projectUid);
    }

    @PostMapping()
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

    @DeleteMapping()
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
    private ResponseEntity<InputDataErrorResponse> handleException(EntityDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "unknownUid",
                resourceBundle.getString("ENTITY_DOES_NOT_EXIST")
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
