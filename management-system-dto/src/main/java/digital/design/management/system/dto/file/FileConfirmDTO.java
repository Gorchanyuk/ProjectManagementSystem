package digital.design.management.system.dto.file;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileConfirmDTO {

    private UUID uid;
    private UUID projectUid;
    private String fileName;
    private String tempDir;
    private String hashcode;
}
