package digital.design.management.system.service;

import digital.design.management.system.dto.file.FileDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


public interface StorageService {

    FileDTO fileUpload(MultipartFile file, UUID uid);

    Resource downloadFile(String filename);

    List<FileDTO> getAllFiles(UUID uid);

    FileDTO fileReplace(String filename, MultipartFile file);

    FileDTO deleteFile(String filename);
}