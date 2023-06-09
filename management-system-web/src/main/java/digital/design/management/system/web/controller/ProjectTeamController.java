package digital.design.management.system.web.controller;

import digital.design.management.system.dto.util.InputDataErrorResponse;
import digital.design.management.system.dto.team.ProjectTeamDTO;
import digital.design.management.system.dto.team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.team.ProjectTeamOutDTO;
import digital.design.management.system.service.ProjectTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project_team")
@Tag(name = "Команды проектов", description = "Контроллер для управления командами проектов")
@Slf4j
public class ProjectTeamController {

    private final ProjectTeamService projectTeamService;

    @Operation(summary = "Получение всех участников проекта",
            description = "Находит всех участников из заданного проекта, поиск осуществляется по uid проекта")
    @GetMapping(value = "/{project_uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectTeamOutDTO> getAllParty(@Parameter(description = "uid проекта, команду которого необходимо получить")
                                               @PathVariable("project_uid") UUID projectUid) {
        return projectTeamService.getAllParty(projectUid);
    }

    @Operation(summary = "Добавление участника",
            description = "Добавление в команду сотрудника")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addParticipant(@Valid @RequestBody ProjectTeamDTO team,
                                                 BindingResult bindingResult) {
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
        return new ResponseEntity<>(projectTeamOutDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление участника",
            description = "Удаление сотрудника из команды")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteParticipant(@RequestBody ProjectTeamDeleteDTO projectTeamDeleteDTO) {
        projectTeamService.deleteParticipant(projectTeamDeleteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
