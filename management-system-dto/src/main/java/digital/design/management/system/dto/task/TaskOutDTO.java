package digital.design.management.system.dto.task;

import digital.design.management.system.enumerate.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель задачи для передачи данных на внешний интерфейс")
public class TaskOutDTO{

    @Schema(description = "Уникальный идентификатор uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID uid;

    @Schema(description = "Наименование задачи", example = "Разработать сервисную часть")
    private String name;

    @Schema(description = "Описание задачи", example = "Детальное описание задачи")
    private String description;

    @Schema(description = "uid bсполнителя задачи", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID taskPerformer;

    @Schema(description = "Трудозатраты, в часах", example = "20")
    private Integer executionTime;

    @Schema(description = "Дата, когда задача должна быть исполнена.Нельзя выбрать дату если дата меньше, чем  дата " +
            "создания + трудозатраты.", example = "14/02/2023")
    private LocalDate deadline;

    @Schema(description = "uid проекта, для которого назначена задача", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID project;

    @Schema(description = "uid автора задачи", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID author;

    @Schema(description = "Статус задачи", example = "NEW", oneOf = StatusTask.class)
    private StatusTask status;

    @Schema(description = "Дата создания зачачи", example = "14/02/2023")
    private LocalDate dateOfCreated;

    @Schema(description = "Дата редактировния зачачи", example = "14/02/2023")
    private LocalDate dateOfUpdate;
}
