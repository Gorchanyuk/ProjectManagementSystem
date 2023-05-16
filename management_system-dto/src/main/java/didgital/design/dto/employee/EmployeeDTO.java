package didgital.design.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String lastName;

    private String firstName;

    private String surname;

    private String jodTitle;

    private String username;

    private String email;
}
