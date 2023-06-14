package digital.design.management.system.util;

import digital.design.management.system.common.exception.StorageSaveFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class FileUtil {

    //Получает хэшкод файла и возвращает его в виде строки
    public String getFileHash(MultipartFile file) {
        MessageDigest md;
        byte[] fileBytes;
        try {
            md = MessageDigest.getInstance("SHA-256");
            fileBytes = file.getBytes();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new StorageSaveFileException();
        }
        byte[] fileHash = md.digest(fileBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : fileHash) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        log.debug("Пot file {} hashcode", file.getOriginalFilename());
        return sb.toString();
    }
}