package digital.design.management.system.dto.task;

import digital.design.management.system.enumerate.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель фильтра для поиска задач")
public class TaskFilterDTO {

    @Schema(description = "Часть текстового значения в поле 'Наименование задачи'", example = "проктирование")
    private String name;

    @Schema(description = "Статус задачи", example = "NEW", oneOf = StatusTask.class)
    private List<StatusTask> status;

    @Schema(description = "uid автора задачи", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID author;

    @Schema(description = "uid исполнителя задачи", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID taskPerformer;

    @Schema(description = "Стартовая дата для поиска в категории 'Крайний срок'", example = "14/02/2023")
    private LocalDate deadlineStart;

    @Schema(description = "Конечная дата для поиска в категории 'Крайний срок'", example = "23/02/2023")
    private LocalDate deadlineEnd;

    @Schema(description = "Стартовая дата для поиска в категории 'Создание проекта'", example = "14/02/2023")
    private LocalDate dateOfCreatedStart;

    @Schema(description = "Конечная дата для поиска в категории 'Создание проекта'", example = "23/02/2023")
    private LocalDate dateOfCreatedEnd;
}
