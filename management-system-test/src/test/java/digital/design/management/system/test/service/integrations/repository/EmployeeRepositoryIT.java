package digital.design.management.system.test.service.integrations.repository;


import digital.design.management.system.app.ManagementSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@DataJpaTest()
@SpringJUnitConfig(ManagementSystem.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIT {


    @Value("${spring.datasource.url}")
    private String url;

    @Test
    public void checkDatasourceUrl() {
        Assertions.assertEquals("jdbc:postgresql://localhost:5432/management_system", url);
    }
}
