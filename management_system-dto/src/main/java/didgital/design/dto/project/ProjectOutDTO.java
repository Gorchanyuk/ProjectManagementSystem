package didgital.design.dto.project;

import didgital.design.common.enumerate.StatusProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectOutDTO extends ProjectDTO{

    private long id;

    private StatusProject statusProject;
}
