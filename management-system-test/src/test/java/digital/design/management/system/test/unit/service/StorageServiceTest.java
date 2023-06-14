package digital.design.management.system.test.unit.service;

import digital.design.management.system.common.exception.FileWithThisHashcodeAlreadyExistsException;
import digital.design.management.system.service.StorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@SpringBootTest
public class StorageServiceTest {

    @Autowired
    private StorageService storageService;

    @Test
    public void  saveNewFileShouldReturnFileName(@TempDir Path tempDir){
        String dir = tempDir.toString();
        UUID uid = UUID.randomUUID();
        String fileName = "test";
        String expectFileName = "test-" + uid;

        shouldReturnFileNameAssert(dir, uid, fileName, expectFileName);

        String fileNameWithDot = fileName + ".txt";
        String expectFileNameWithDot = expectFileName + ".txt";

        shouldReturnFileNameAssert(dir, uid, fileNameWithDot, expectFileNameWithDot);
    }

    private void shouldReturnFileNameAssert(String dir, UUID uid, String fileName, String expectFileName) {

        String content = "Hello, World!";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "text/plain", content.getBytes());

        String resultFileName = storageService.saveNewFile(file, dir, uid);

        Assertions.assertEquals(expectFileName, resultFileName);
    }

    @Test
    public void saveShouldSaveFile(@TempDir Path tempDir) throws IOException {
        String dir = tempDir.toString();
        String fileName = "test.txt";
        String content = "Hello, World!";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "text/plain", content.getBytes());

        storageService.save(file, dir, fileName);

        File dirFile = new File(dir, fileName);
        Assertions.assertTrue(dirFile.exists());
        // Проверяем, что содержимое файла соответствует ожидаемому
        String fileContent = new String(Files.readAllBytes(dirFile.toPath()));
        Assertions.assertEquals(content, fileContent);
    }

    @Test
    public void saveFileInTempDirShouldThrowFileWithThisHashcodeAlreadyExistsException(){
        UUID entityUid = UUID.randomUUID();
        String hashcode = "hashcode";
        MockMultipartFile file = new MockMultipartFile("test.txt", "Hello, World!".getBytes());

        Assertions.assertThrows(FileWithThisHashcodeAlreadyExistsException.class,
                ()->storageService.saveFileInTempDir(file, entityUid, hashcode));
    }
}
