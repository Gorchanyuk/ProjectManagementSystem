package digital.design.management.system.dto.task;

import digital.design.management.system.dto.task.TaskDTO;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Модель задачи для передачи данных на внешний интерфейс")
public class TaskOutDTO extends TaskDTO {

    @Schema(description = "Уникальный идентификатор uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID uid;

    @Schema(description = "uid автора задачи", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID author;

    @Schema(description = "Дата создания зачачи", example = "14.02.2023")
    private Date dateOfCreated;

    @Schema(description = "Дата редактировния зачачи", example = "14.02.2023")
    private Date dateOfUpdate;
}
