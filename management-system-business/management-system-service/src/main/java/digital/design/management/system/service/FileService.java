package digital.design.management.system.service;

import digital.design.management.system.common.exception.StorageFileNotFoundException;
import digital.design.management.system.common.exception.StorageSaveFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
public class FileService {

    public String saveNewFile(MultipartFile file, String dir, UUID uid) {
        try {
            Path directory = Path.of(dir);
            if (Files.notExists(directory)){
                Files.createDirectories(directory);
                log.info("Directory {} is created", dir);
            }
        } catch (IOException e) {
            throw new StorageSaveFileException();
        }
            String fileName = file.getOriginalFilename();

            String name = Objects.requireNonNull(fileName).substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));
            String newFileName = name + "-" + uid + extension;

            save(file, dir,  newFileName);

            return newFileName;
    }

    public void save(MultipartFile file, String dir, String filename) {
        try {
            Path path = Paths.get(dir, filename);
            Files.write(path, file.getBytes());
            log.debug("File {} saved", filename);
        } catch (IOException e) {
            throw new StorageSaveFileException();
        }
    }

    public Resource downloadFile(String dir, String filename) {
        Path path = Paths.get(dir, filename);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException();
        }
    }

    public void deleteFile(String dir, String filename){
        Path path = Paths.get(dir, filename);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageFileNotFoundException();
        }
    }
}
