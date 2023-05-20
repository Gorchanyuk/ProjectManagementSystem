package digital.design.management.system.dto.task;

import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.enumerate.StatusTask;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    @NotBlank(message = "Поле 'Наименование задачи' не может быть пустым")
    private String name;

    private String description;

    private Employee taskPerformer;

    @NotBlank(message = "Поле 'Трудозатраты' не может быть пустым")
    private int executionTime;

    @NotBlank(message = "Поле 'Крайний срок' не может быть пустым")
    private Date deadline;

    @NotBlank(message = "Поле 'Статус задачи' не может быть пустым")
    private StatusTask status;

    private Employee author;

    private Date dateOfCreated;

    private Date dateOfUpdate;

    private Project project;
}
