package digital.design.management.system.entity;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.util.ProjectTeamId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_team")
@IdClass(ProjectTeamId.class)
@NamedEntityGraph(
        name = "project_employee",
        attributeNodes = {
                @NamedAttributeNode("projectId"),
                @NamedAttributeNode("employeeId")
        })
public class ProjectTeam {

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projectId;          //Проект

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeId;        //Участник проекта

    @Enumerated(EnumType.STRING)
    @Column(name = "role_employee", nullable = false)
    private RoleEmployee roleEmployee;  //Роль участника проекта
}