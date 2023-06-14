package digital.design.management.system.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "props.init.admin")
public class AdminInitProperty {

    private String username;
    private String password;
}
