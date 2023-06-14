package digital.design.management.system.validator;

import digital.design.management.system.dto.task.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class TaskValidator implements Validator {

    private final ResourceBundle resourceBundle;

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskDTO taskDTO = (TaskDTO) target;

        LocalDate localDate = LocalDate.now()
                .plusDays((int) Math.ceil(taskDTO.getExecutionTime() / 8.0));
        if (taskDTO.getDeadline().isBefore(localDate)) {
            errors.rejectValue("deadline", "", resourceBundle.getString("DEADLINE_IS_TOO_SHORT"));
        }
    }
}
