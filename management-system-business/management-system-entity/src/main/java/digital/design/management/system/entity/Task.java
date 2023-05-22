package digital.design.management.system.entity;

import digital.design.management.system.converter.StatusTaskConverter;
import digital.design.management.system.enumerate.StatusTask;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "performer")
    private Employee taskPerformer;

    @Column(name = "execution_time", nullable = false)
    private int executionTime;

    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @Column(name = "status", nullable = false)
    @Convert(converter = StatusTaskConverter.class)
    private StatusTask status;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private Employee author;

    @Column(name = "date_of_created", nullable = false)
    private Date dateOfCreated;

    @Column(name = "date_of_update", nullable = false)
    private Date dateOfUpdate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}