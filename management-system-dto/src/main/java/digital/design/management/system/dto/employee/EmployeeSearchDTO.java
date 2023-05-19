package digital.design.management.system.dto.employee;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.common.enumerate.StatusProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchDTO {

    StatusProject statusProject;

    RoleEmployee roleEmployee;
}
