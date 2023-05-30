package digital.design.management.system.dto.util;

import lombok.*;

//Класс для представления ошибки
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputDataErrorResponse {

    private String field;
    private String defaultMessage;
}