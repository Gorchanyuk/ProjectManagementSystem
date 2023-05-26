package digital.design.management.system.security;

import digital.design.management.system.entity.Employee;
import digital.design.management.system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final ResourceBundle resourceBundle;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Employee> employee = employeeRepository.findByUsernameOrEmail(username, username);
        if (employee.isEmpty())
            throw new UsernameNotFoundException(resourceBundle.getString("EMPLOYEE_BY_USERNAME_DOES_NOT_EXIST"));

        return new EmployeeDetails(employee.get());
    }
}