package digital.design.management.system.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель для установления зависимотстей между задачами")
public class TaskChildDTO {

    @Schema(description = "Список задач на которые нужно установить зависимость")
    public List<UUID> children;
}
