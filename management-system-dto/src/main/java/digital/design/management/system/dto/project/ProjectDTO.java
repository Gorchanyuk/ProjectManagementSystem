package digital.design.management.system.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Schema(description = "Модель проекта для передачи данных на сервер")
public class ProjectDTO {


    @NotBlank(message = "Поле 'Код проекта' не может быть пустым")
    @Schema(description = "Код проекта", example = "project 1.1.0")
    private String code;

    @NotBlank(message = "Поле 'Наименование' не может быть пустым")
    @Schema(description = "Наименование", example = "Система управлениями проектами")
    private String name;

    @Schema(description = "Описание", example = "Детальное описание проекта")
    private String description;


}
