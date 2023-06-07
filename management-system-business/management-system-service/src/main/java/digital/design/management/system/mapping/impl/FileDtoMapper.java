package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.entity.ProjectFile;
import digital.design.management.system.entity.TaskFile;
import org.springframework.stereotype.Component;

@Component
public class FileDtoMapper {

    public FileDTO getDto (ProjectFile entityFile){
        return FileDTO.builder()
                .uid(entityFile.getUid())
                .fileName(entityFile.getFilename())
                .build();
    }

    public FileDTO getDto (TaskFile entityFile){
        return FileDTO.builder()
                .uid(entityFile.getUid())
                .fileName(entityFile.getFilename())
                .build();
    }

}
