package digital.design.management.system.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "digital.design.management.system.entity")
public class EntityConfiguration {
}
