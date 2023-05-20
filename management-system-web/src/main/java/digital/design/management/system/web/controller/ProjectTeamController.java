package digital.design.management.system.web.controller;

import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.common.util.ProjectTeamValidator;
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
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project_team")
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;
    private final ProjectTeamValidator projectTeamValidator;

    @GetMapping("/{project_id}")
    public List<ProjectTeamOutDTO> getAllParty(@PathVariable("project_id") UUID projectId) {
        return projectTeamService.getAllParty(projectId);
    }

    @PostMapping()
    public ResponseEntity<Object> addParticipant(@Valid @RequestBody ProjectTeamDTO team,
                                                 BindingResult bindingResult) {
        System.out.println("twew");
        projectTeamValidator.validate(team, bindingResult);
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

    /* HttpMessageNotReadableException - это исключение в данном случае сообщает что в качестве аргумента
        поступил несовместимый тип, поэтому возвращаем сообщение с перечислением допустимых значений */
    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(HttpMessageNotReadableException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "status",
                "Роль сотрудника должна быть представлена одним из следующих вариантов " +
                        "PROJECT_MANAGER, ANALYST, DEVELOPER или TESTER"
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
