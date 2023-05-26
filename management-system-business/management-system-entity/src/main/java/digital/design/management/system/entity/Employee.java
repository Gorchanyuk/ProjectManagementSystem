package digital.design.management.system.entity;

import digital.design.management.system.enumerate.StatusEmployee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    public Employee() {
        this.uid = UUID.randomUUID();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "password")
    private String password;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEmployee status;

}
