package didgital.design.dto.project;

import didgital.design.common.enumerate.StatusProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private String code;

    private String name;

    private String description;

}
