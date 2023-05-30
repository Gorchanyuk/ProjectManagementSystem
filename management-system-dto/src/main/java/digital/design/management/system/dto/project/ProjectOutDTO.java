package digital.design.management.system.dto.project;

import digital.design.management.system.common.enumerate.StatusProject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель проекта для передачи данных на внешний интерфейс")
public class ProjectOutDTO{

    @Schema(description = "Уникальный идентификатор uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID uid;

    @Schema(description = "Статус", example = "DRAFT", oneOf = StatusProject.class)
    private StatusProject status;

    @Schema(description = "Код проекта", example = "project 1.1.0")
    private String code;

    @Schema(description = "Наименование", example = "Система управлениями проектами")
    private String name;

    @Schema(description = "Описание", example = "Детальное описание проекта")
    private String description;

}
