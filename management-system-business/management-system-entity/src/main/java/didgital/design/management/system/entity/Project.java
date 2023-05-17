package didgital.design.management.system.entity;

import didgital.design.management.system.common.enumerate.StatusProject;
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
