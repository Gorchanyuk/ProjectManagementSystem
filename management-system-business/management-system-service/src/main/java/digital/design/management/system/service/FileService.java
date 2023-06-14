package digital.design.management.system.service;

import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.dto.file.FileTokenDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


public interface FileService {

    FileDTO fileUpload(MultipartFile file, UUID uid);

    Resource downloadFile(UUID uid);

    List<FileDTO> getAllFiles(UUID uid);

    FileDTO fileReplace(UUID uid, MultipartFile file);

    FileDTO deleteFile(UUID uid);

    FileDTO confirmUploadFile(FileTokenDTO tokenDTO);
}