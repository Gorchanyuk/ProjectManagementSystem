package digital.design.management.system.entity;

import digital.design.management.system.converter.RoleEmployeeConverter;
import digital.design.management.system.enumerate.RoleEmployee;
import digital.design.management.system.ProjectTeamId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_team")
@IdClass(ProjectTeamId.class)
@NamedEntityGraph(
        name = "project.allEmployee",
        attributeNodes = {
                @NamedAttributeNode("projectId"),
                @NamedAttributeNode("employeeId")
        })
public class ProjectTeam {

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projectId;

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    @Column(name = "role_employee", nullable = false)
    @Convert(converter = RoleEmployeeConverter.class)
    private RoleEmployee roleEmployee;

    public ProjectTeam(Project project, Employee employee){
        projectId = project;
        employeeId = employee;
    }


}