package didgital.design.management.system.entity;

import didgital.design.management.system.common.enumerate.StatusProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Entity {

    private Long id;

    private String code;

    private String name;

    private String description;

    private StatusProject status;
}
