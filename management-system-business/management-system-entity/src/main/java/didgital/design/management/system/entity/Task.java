package didgital.design.management.system.entity;

import didgital.design.management.system.common.enumerate.StatusTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Entity {

    private Long id;

    private String name;

    private String description;

    private Employee taskPerformer;

    private Integer executionTime;

    private Date deadline;

    private StatusTask status;

    private Employee author;

    private Date dateOfCreated;

    private Date dateOfUpdate;

    private Project project;
}
