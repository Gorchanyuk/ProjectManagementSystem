package digital.design.management.system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="project_file")
@NamedEntityGraph(
        name = "project_file",
        attributeNodes = {
                @NamedAttributeNode("projectId")
        })
public class ProjectFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project projectId;

    @Column(name = "filename")
    private String filename;
}
