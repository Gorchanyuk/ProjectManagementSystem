package digital.design.management.system.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель файла")
public class FileDTO {
    @Schema(description = "Название файла", example = "text-123.txt")
    private String fileName;
}

