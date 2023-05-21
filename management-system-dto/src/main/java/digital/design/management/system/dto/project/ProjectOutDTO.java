package digital.design.management.system.dto.project;

import digital.design.management.system.enumerate.StatusProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOutDTO extends ProjectDTO{

    private UUID uid;

    private StatusProject status;

}
