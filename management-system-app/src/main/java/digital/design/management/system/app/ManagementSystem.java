package digital.design.management.system.app;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("digital.design.management.system")
@EnableJpaRepositories(basePackages = "digital.design.management.system.repository")
@EntityScan(basePackages = "digital.design.management.system.entity")
@RequiredArgsConstructor
public class ManagementSystem implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ManagementSystem.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
