package digital.design.management.system.validator;


import digital.design.management.system.enumerate.StatusEmployee;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class EmployeeValidator implements Validator {

    private final EmployeeRepository employeeRepository;

    @Override
    public boolean supports(Class<?> clazz) {

        return EmployeeDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO employee = (EmployeeDTO) target;

        if (employeeRepository.findByUsernameAndStatus(employee.getUsername(), StatusEmployee.ACTIV).isPresent()) {
            errors.rejectValue("username", "", "Сотрудник с такой учетной записью уже существует");
        }
    }
}
