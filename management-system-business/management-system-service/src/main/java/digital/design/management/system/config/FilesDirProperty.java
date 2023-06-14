package digital.design.management.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "props.files")
public class FilesDirProperty {

    private String taskDir;
    private String projectDir;
}
