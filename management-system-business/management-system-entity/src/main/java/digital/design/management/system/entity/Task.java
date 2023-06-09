package digital.design.management.system.entity;

import digital.design.management.system.common.enumerate.StatusTask;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "task")
@NamedEntityGraph(
        name = "task",
        attributeNodes = {
                @NamedAttributeNode("taskPerformer"),
                @NamedAttributeNode("author"),
                @NamedAttributeNode("project")
        })
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "name", nullable = false)
    private String name;            //Наименование задачи

    @Column(name = "description")
    private String description;     //Описание задачи

    @ManyToOne
    @JoinColumn(name = "performer")
    private Employee taskPerformer; //Исполнитель задачи

    @Column(name = "execution_time", nullable = false)
    private Integer executionTime;  //Трудозатраты

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;     //Крайний срок сдачи

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTask status;      //Статус задачи

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private Employee author;        //Автор задачи

    @Column(name = "date_of_created", nullable = false)
    private LocalDate dateOfCreated;//Дата создания задачи

    @Column(name = "date_of_update", nullable = false)
    private LocalDate dateOfUpdate; //Дата последнего изменения задачи

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;        //Проект к которому относится задача

    @ManyToOne
    @JoinColumn(name = "task_parent")
    private Task taskParent;        //Задача которая зависит от текущей задачи
}