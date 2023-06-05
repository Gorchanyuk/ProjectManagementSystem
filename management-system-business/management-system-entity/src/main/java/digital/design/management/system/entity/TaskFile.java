package digital.design.management.system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="task_file")
@NamedEntityGraph(
        name = "task_file",
        attributeNodes = {
                @NamedAttributeNode("taskId")
        })
public class TaskFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task taskId;

    @Column(name = "filename")
    private String filename;
}
