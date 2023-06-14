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
@Table(name="task_file")
@NamedEntityGraph(
        name = "task_file",
        attributeNodes = {
                @NamedAttributeNode("taskId")
        })
public class TaskFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //Уникальный идентификатор, используется для связей внутри БД

    @Column(name = "uid")
    private UUID uid;           //Уникальный идентификатор, используется для внешних систем

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task taskId;        //Задача к которой прикреплен файл

    @Column(name = "filename", nullable = false)
    private String filename;    //Название файла

    @Column(name = "hashcode", nullable = false)
    private String hashcode;    //hashcode файла
}
