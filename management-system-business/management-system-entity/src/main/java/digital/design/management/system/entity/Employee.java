package digital.design.management.system.entity;

import digital.design.management.system.converter.StatusEmployeeConverter;
import digital.design.management.system.enumerate.StatusEmployee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    public Employee(){
        this.uid = UUID.randomUUID();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uid;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "job_title")
    private String jodTitle;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "status", nullable = false)
    @Convert(converter = StatusEmployeeConverter.class)
    private StatusEmployee status;

    @ManyToMany
    @JoinTable(
            name = "project_team",
            joinColumns =  @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects;

}
