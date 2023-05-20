package digital.design.management.system.common.util;


import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProjectValidator implements Validator {

    private final ProjectRepository projectRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectDTO projectDTO = (ProjectDTO) target;

        if(projectRepository.findByCode(projectDTO.getCode()).isPresent()){
            errors.rejectValue("code", "", "Проект с таким кодом уже существует");
        }
    }
}
