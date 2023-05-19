package digital.design.management.system.entity;

import digital.design.management.system.common.enumerate.StatusProject;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Project implements Entity {

    private Long id;

    private String code;

    private String name;

    private String description;

    private StatusProject status;
}
