package digital.design.management.system.dto.project;

import digital.design.management.system.enumerate.StatusProject;
import digital.design.management.system.dto.project.ProjectDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель проекта для передачи данных на внешний интерфейс")
public class ProjectOutDTO extends ProjectDTO {

    @Schema(description = "Уникальный идентификатор uid", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID uid;

    @Schema(description = "Статус", example = "DRAFT", oneOf = StatusProject.class)
    private StatusProject status;

}
