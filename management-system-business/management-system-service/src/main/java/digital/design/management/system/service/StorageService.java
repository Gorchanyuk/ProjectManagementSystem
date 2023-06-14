package digital.design.management.system.service;

import digital.design.management.system.common.exception.FileWithThisHashcodeAlreadyExistsException;
import digital.design.management.system.common.exception.StorageFileNotFoundException;
import digital.design.management.system.common.exception.StorageSaveFileException;
import digital.design.management.system.dto.file.FileConfirmDTO;
import digital.design.management.system.util.TokenForConfirmUploadFile;
import lombok.RequiredArgsConstructor;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageService {

    private final TokenForConfirmUploadFile tokenForConfirmUploadFile;

    public String saveNewFile(MultipartFile file, String dir, UUID uid) {
        checkAndCreateDirectory(dir);
        String fileName = file.getOriginalFilename();
        fileName = getNewName(uid, fileName);

        save(file, dir,  fileName);

            return fileName;
    }

    private static String getNewName(UUID uid, String fileName) {
        if(Objects.requireNonNull(fileName).contains(".")) {
            String name = Objects.requireNonNull(fileName).substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));
            fileName = name + "-" + uid + extension;
        }else{
            fileName = fileName + "-" + uid;
        }
        return fileName;
    }

    public void save(MultipartFile file, String dir, String filename) {
        try {
            Path path = Paths.get(dir, filename);
            Files.write(path, file.getBytes());
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

    private static void checkAndCreateDirectory(String dir) {
        try {
            Path directory = Path.of(dir);
            if (Files.notExists(directory)){
                Files.createDirectories(directory);
                log.info("Directory {} created", dir);
            }
        } catch (IOException e) {
            throw new StorageSaveFileException();
        }
    }

    public FileConfirmDTO moveFile(String token, String dir) throws IOException {
        FileConfirmDTO dto = tokenForConfirmUploadFile.validateTokenAndRetrieveClaim(token);
        String filename = dto.getFileName();
        Path tempPath = Path.of(dto.getTempDir());
        Path destinationPath = Path.of(dir, filename);
        Files.move(tempPath, destinationPath);

        return dto;
    }

    public void saveFileInTempDir(MultipartFile file, UUID entityUid, String hashcode) throws IOException {

        UUID fileUid = UUID.randomUUID();
        String newFileName = getNewName(fileUid,file.getOriginalFilename());
        Path tempPath = Files.createTempFile(null, newFileName);
        Files.write(tempPath, file.getBytes());
        String token = tokenForConfirmUploadFile.generateToken(newFileName, fileUid, entityUid, tempPath.toString(), hashcode);
        throw new FileWithThisHashcodeAlreadyExistsException(token);
    }

}
