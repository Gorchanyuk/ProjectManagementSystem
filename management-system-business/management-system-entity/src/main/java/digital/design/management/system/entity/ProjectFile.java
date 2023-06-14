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
    private Long id;            //Уникальный идентификатор, используется для связей внутри БД

    @Column(name = "uid")
    private UUID uid;           //Уникальный идентификатор, используется для внешних систем

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project projectId;  //Проект к которому прикреплен файл

    @Column(name = "filename", nullable = false)
    private String filename;    //Название файла

    @Column(name = "hashcode", nullable = false)
    private String hashcode;
}
