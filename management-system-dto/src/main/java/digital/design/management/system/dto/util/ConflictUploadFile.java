package digital.design.management.system.dto.util;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConflictUploadFile {

    private String message;
    private String url;
    private String token;
}
