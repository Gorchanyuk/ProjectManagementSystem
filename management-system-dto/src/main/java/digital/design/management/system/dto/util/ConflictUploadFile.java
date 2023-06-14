package digital.design.management.system.dto.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель для сообщения о необходимости подтвердить сохранение файла")
public class ConflictUploadFile {

    @Schema(description = "Сообщение с информацией о конфликте")
    private String message;
    @Schema(description = "URL по которому нужно перейти для подтверждения сохранения файла", example = "http://localhost:8080/project/file/confirm")
    private String url;
    @Schema(description = "Токен который нужно передать при подтверждении сохранения")
    private String token;
}
