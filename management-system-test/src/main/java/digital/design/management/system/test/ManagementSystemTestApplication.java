package digital.design.management.system.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("digital.design.management.system")
public class ManagementSystemTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementSystemTestApplication.class, args);
    }


}
