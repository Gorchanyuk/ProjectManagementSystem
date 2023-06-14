package digital.design.management.system.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель для подтверждения сохранения файла")
public class FileTokenDTO {

    @Schema(description = "Токен, выданный при неудачной попытке сохранить файл")
    private String token;
}
