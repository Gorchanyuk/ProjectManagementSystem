package digital.design.management.system.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TempFileCleanup {
    
    public void cleanup(Path tempFilePath){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        try{
            executor.schedule(() -> {
                try {
                    Files.deleteIfExists(tempFilePath);
                    log.debug("File {} has been removed from the temporary directory", tempFilePath);
                } catch (IOException e) {
                }
            }, 30, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}