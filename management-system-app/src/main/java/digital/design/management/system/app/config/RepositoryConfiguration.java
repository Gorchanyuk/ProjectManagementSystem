package digital.design.management.system.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "digital.design.management.system.repository")
public class RepositoryConfiguration {
}
