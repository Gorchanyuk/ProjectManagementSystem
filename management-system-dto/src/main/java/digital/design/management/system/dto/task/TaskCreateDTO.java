package digital.design.management.system.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Schema(description = "Модель задачи для создания задачи и передачи на сервер")
public class TaskCreateDTO extends TaskDTO{

    @NotNull(message = "Задача должна быть связана с проектом")
    @Schema(description = "uid проекта, для которого назначена задача", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID project;

}
