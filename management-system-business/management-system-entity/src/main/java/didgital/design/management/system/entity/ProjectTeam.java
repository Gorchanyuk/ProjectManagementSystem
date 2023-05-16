package didgital.design.management.system.entity;

import didgital.design.management.system.common.enumerate.RoleEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTeam{

    private Project project_id;

    private Employee employee_id;

    private RoleEmployee roleEmployee;
}
