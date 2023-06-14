package digital.design.management.system.dto.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

//Класс для представления ошибки
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель для передачи сообщения об ошибке")
public class InputDataErrorResponse {

    @Schema(description = "Поле или краткая информация о расположении ошибки")
    private String field;
    @Schema(description = "Информация об ошибке")
    private String defaultMessage;
}