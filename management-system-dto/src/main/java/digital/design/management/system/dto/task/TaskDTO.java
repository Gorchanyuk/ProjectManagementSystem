package digital.design.management.system.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Schema(description = "Модель задачи для передачи данных на сервер")
public class TaskDTO {

    @NotBlank(message = "Поле 'Наименование задачи' не может быть пустым")
    @Schema(description = "Наименование задачи", example = "Разработать сервисную часть")
    private String name;

    @Schema(description = "Описание задачи", example = "Детальное описание задачи")
    private String description;

    @Schema(description = "uid исполнителя задачи", example = "ccd90ae1-a3db-46be-83cb-ead8ed03f732")
    private UUID taskPerformer;

    @NotNull(message = "Поле 'Трудозатраты' не может быть пустым")
    @Schema(description = "Трудозатраты, в часах", example = "20")
    private Integer executionTime;

    @NotNull(message = "Поле 'Крайний срок' не может быть пустым")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Дата, когда задача должна быть исполнена.Нельзя выбрать дату если дата меньше, чем  дата " +
            "создания + трудозатраты. Формат 14/02/2023", example = "14/02/2023")
    private LocalDate deadline;

}
