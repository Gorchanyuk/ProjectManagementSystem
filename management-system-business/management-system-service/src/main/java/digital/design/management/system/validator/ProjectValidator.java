package digital.design.management.system.validator;


import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class ProjectValidator implements Validator {

    private final ProjectRepository projectRepository;
    private final ResourceBundle resourceBundle;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectDTO projectDTO = (ProjectDTO) target;

        if (projectRepository.findByCode(projectDTO.getCode()).isPresent()) {
            errors.rejectValue("code", "", resourceBundle.getString("SUCH_CODE_PROJECT_ALREADY_EXIST"));
        }
    }
}
