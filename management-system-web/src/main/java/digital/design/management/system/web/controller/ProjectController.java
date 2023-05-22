package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.EntityDoesNotExistException;
import digital.design.management.system.common.exception.StatusProjectHasNotNextStatusException;
import digital.design.management.system.common.exception.SuchCodeProjectAlreadyExistException;
import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.validator.ProjectValidator;
import digital.design.management.system.converter.StatusProjectConverter;
import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.enumerate.StatusProject;
import digital.design.management.system.service.ProjectService;
import jakarta.persistence.Convert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectValidator projectValidator;
    private final ResourceBundle resourceBundle;

    @GetMapping()
    public List<ProjectOutDTO> getProjects() {
        return projectService.getProjects();
    }

    @PostMapping()
    public ResponseEntity<Object> createProject(@Valid @RequestBody ProjectDTO projectDTO,
                                                BindingResult bindingResult){

        projectValidator.validate(projectDTO, bindingResult);
        if (bindingResult.hasErrors()){
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e->InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        ProjectOutDTO projectOutDTO = projectService.createProject(projectDTO);

        return new ResponseEntity<>(projectOutDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Object> updateProject(@PathVariable("uid") UUID uid,
                                                @Valid @RequestBody ProjectDTO projectDTO,
                                                BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e->InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }

        ProjectOutDTO projectOutDTO = projectService.updateProject(uid, projectDTO);
        return new ResponseEntity<>(projectOutDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    @Convert(converter = StatusProjectConverter.class)
    public List<ProjectOutDTO> getProjectsBySearch(@RequestParam(value = "key", defaultValue = "") String key,
                                                   @RequestParam(value = "status", defaultValue = "DRAFT,DEVELOP,TEST,COMPLETE")
                                                   List<StatusProject> statuses){

        return projectService.getProjectsBySearch( key, statuses);
    }

    @PatchMapping("/{uid}")
    public ProjectOutDTO updateStatusProject(@PathVariable("uid") UUID uid){
        return projectService.updateStatusProject(uid);
    }


    /* MethodArgumentTypeMismatchException - это исключение в данном случае сообщает что в качестве аргумента
    поступил несовместимый тип, поэтому возвращаем сообщение с перечислением допустимых значений */
    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(MethodArgumentTypeMismatchException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "status",
                resourceBundle.getString("STATUS_PROJECT_EXCEPTION")
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(SuchCodeProjectAlreadyExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "code",
                resourceBundle.getString("SUCH_CODE_PROJECT_ALREADY_EXIST")
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EntityDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "id",
                resourceBundle.getString("PROJECT_DOES_NOT_EXIST")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(StatusProjectHasNotNextStatusException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "status",
                resourceBundle.getString("STATUS_PROJECT_HAS_NOT_NEXT_STATUS")
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
