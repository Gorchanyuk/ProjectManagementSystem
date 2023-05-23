package digital.design.management.system.entity;

import digital.design.management.system.converter.StatusTaskConverter;
import digital.design.management.system.enumerate.StatusTask;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "task")
public class Task{

    public Task(){this.uid = UUID.randomUUID();}

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
    private Integer executionTime;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "status", nullable = false)
    @Convert(converter = StatusTaskConverter.class)
    private StatusTask status;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private Employee author;

    @Column(name = "date_of_created", nullable = false)
    private LocalDate dateOfCreated;

    @Column(name = "date_of_update", nullable = false)
    private LocalDate dateOfUpdate;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}