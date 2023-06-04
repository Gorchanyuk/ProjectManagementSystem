package digital.design.management.system.web.controller;

import digital.design.management.system.dto.util.InputDataErrorResponse;
import digital.design.management.system.validator.ProjectValidator;
import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.common.enumerate.StatusProject;
import digital.design.management.system.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/project")
@Tag(name = "Проекты", description = "Контроллер для управления проектами")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectValidator projectValidator;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Находит все проекты, но не более 100")
    public List<ProjectOutDTO> getProjects() {
        log.debug("GET request on .../project");
        return projectService.getProjects();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Создание нового проекта. Проекту присваивается статус 'Черновик'")
    public ResponseEntity<Object> createProject(@Valid @RequestBody ProjectDTO projectDTO,
                                                BindingResult bindingResult) {
        log.debug("POST request on .../project, params: projectDTO={}",  projectDTO);
        projectValidator.validate(projectDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            log.warn("POST request on .../project contains errors: {}", infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        ProjectOutDTO projectOutDTO = projectService.createProject(projectDTO);

        log.debug("POST request on .../project is complete");
        return new ResponseEntity<>(projectOutDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{uid}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление данных проекта")
    public ResponseEntity<Object> updateProject(@PathVariable("uid") UUID uid,
                                                @Valid @RequestBody ProjectDTO projectDTO,
                                                BindingResult bindingResult) {
        log.debug("PUT request on .../project/{}", uid);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();
            log.warn("PUT request on .../project{} contains errors: {}", uid, infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }

        ProjectOutDTO projectOutDTO = projectService.updateProject(uid, projectDTO);
        log.debug("PUT request on .../project is complete");
        return new ResponseEntity<>(projectOutDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Поиск проекта по ключевому слову и статусу. Поиск осуществляется по полям код проекта и название проекта. " +
            "Если не указать статус поиск осуществляется по всем проектам")
    public List<ProjectOutDTO> getProjectsBySearch(@RequestParam(value = "key", defaultValue = "") String key,
                                                   @RequestParam(value = "status", defaultValue = "DRAFT,DEVELOP,TEST,COMPLETE")
                                                   List<StatusProject> statuses) {
        log.debug("GET request on .../project/search, params: key={}, status={}",  key, statuses);
        return projectService.getProjectsBySearch(key, statuses);
    }

    @PutMapping(value = "/raise_status/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Повышение стаутса проекта")
    public ProjectOutDTO updateStatusProject(@PathVariable("uid") UUID uid,
                                             @RequestParam("status") StatusProject statusProject) {
        log.debug("PUT request on .../raise_status/{}}",  uid);
        return projectService.updateStatusProject(uid, statusProject);
    }

}
