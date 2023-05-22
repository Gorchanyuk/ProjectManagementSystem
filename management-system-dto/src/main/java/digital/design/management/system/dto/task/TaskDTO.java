package digital.design.management.system.dto.task;

import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.enumerate.StatusProject;
import digital.design.management.system.enumerate.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель задачи для передачи данных на сервер")
public class TaskDTO {

    @NotBlank(message = "Поле 'Наименование задачи' не может быть пустым")
    @Schema(description = "Наименование задачи", example = "Разработать сервисную часть")
    private String name;

    @Schema(description = "Описание задачи", example = "Детальное описание задачи")
    private String description;

    @Schema(description = "uid bсполнителя задачи", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID taskPerformer;

    @NotBlank(message = "Поле 'Трудозатраты' не может быть пустым")
    @Schema(description = "Трудозатраты, в часах", example = "20")
    private int executionTime;

    @NotBlank(message = "Поле 'Крайний срок' не может быть пустым")
    @Schema(description = "Дата, когда задача должна быть исполнена.Нельзя выбрать дату если дата меньше, чем  дата " +
            "создания + трудозатраты.", example = "14.02.2023")
    private Date deadline;

    @NotBlank(message = "Поле 'Статус задачи' не может быть пустым")
    @Schema(description = "Статус задачи", example = "NEW", oneOf = StatusTask.class)
    private StatusTask status;

    @Schema(description = "uid проекта, для которого назначена задача", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID project;
}
