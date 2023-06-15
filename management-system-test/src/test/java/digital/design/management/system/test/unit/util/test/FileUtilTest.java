package digital.design.management.system.test.unit.util.test;

import digital.design.management.system.util.FileUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@SpringBootTest
public class FileUtilTest {

    @Autowired
    private FileUtil fileUtil;

    @Test
    void getFileHashShouldReturnResult() throws IOException, NoSuchAlgorithmException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        );

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = file.getBytes();
        byte[] fileHash = md.digest(fileBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : fileHash) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        String expectedHash = sb.toString();

        String actualHash = fileUtil.getFileHash(file);

        Assertions.assertEquals(expectedHash, actualHash);
    }
}
