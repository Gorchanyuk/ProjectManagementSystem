package didgital.design.entity;

import didgital.design.common.converter.StatusTaskConverter;
import didgital.design.common.enumerate.StatusTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private long id;

    private String name;

    private String description;

    private Employee taskPerformer;

    private int executionTime;

    private Date deadline;

    private StatusTask status;

    private Employee author;

    private Date dateOfCreated;

    private Date dateOfUpdate;

    private Project project;
}
