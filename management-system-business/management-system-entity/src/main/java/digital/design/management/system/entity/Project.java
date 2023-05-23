package digital.design.management.system.entity;


import digital.design.management.system.converter.StatusProjectConverter;
import digital.design.management.system.enumerate.StatusProject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "project")
public class Project {

    public Project() {
        this.uid = UUID.randomUUID();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Convert(converter = StatusProjectConverter.class)
    private StatusProject status;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "project")
    @Transient
    private Set<Task> tasks;
}
