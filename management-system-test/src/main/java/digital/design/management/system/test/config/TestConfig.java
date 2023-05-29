package digital.design.management.system.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class TestConfig {

    /*Этот бин нужен для корректной работы тестов.
     * Он анализирует все настройки конфигурации Spring MVC, включая WebMvcConfigurerAdapter, и собирает информацию о том,
     * какие URL-адреса должны быть обработаны каким контроллером и методом. */
    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
}
