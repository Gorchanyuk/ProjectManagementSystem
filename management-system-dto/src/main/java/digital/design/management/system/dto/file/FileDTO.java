package digital.design.management.system.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель файла")
public class FileDTO {

    @Schema(description = "uid файла", example = "00132d94-d1a5-45d8-a689-04916689f9f3")
    private UUID uid;

    @Schema(description = "Название файла", example = "text-123.txt")
    private String fileName;
}

