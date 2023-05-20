package digital.design.management.system.dto.project;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {



    @NotBlank(message = "Поле 'Код проекта' не может быть пустым")
    private String code;

    @NotBlank(message = "Поле 'Наименование' не может быть пустым")
    private String name;

    private String description;


}
