package didgital.design.entity;

import didgital.design.common.converter.StatusProjectConverter;
import didgital.design.common.enumerate.StatusProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private long id;

    private String code;

    private String name;

    private String description;

    private StatusProject status;
}
