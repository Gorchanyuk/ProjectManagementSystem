package digital.design.management.system.dto.project;

import digital.design.management.system.common.enumerate.StatusProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectOutDTO extends ProjectDTO{

    private Long id;

    private StatusProject statusProject;
}
