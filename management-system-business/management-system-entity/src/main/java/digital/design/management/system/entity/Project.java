package digital.design.management.system.entity;

import digital.design.management.system.common.enumerate.StatusProject;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //Уникальный идентификатор, используется для связей внутри БД

    @Column(name = "uid")
    private UUID uid;           //Уникальный идентификатор, используется для внешних систем

    @Column(name = "code", nullable = false, unique = true)
    private String code;        //Код проекта

    @Column(name = "name", nullable = false)
    private String name;        //Наименование проекта

    @Column(name = "description")
    private String description; //Описание проекта

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProject status;//Статус проекта

}
