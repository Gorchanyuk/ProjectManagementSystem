package digital.design.management.system.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ResourceBundle getResourceBundle() {
        Locale locale =
                new Locale.Builder()
                        .setLanguage("ru")
                        .setRegion("RU")
                        .build();

        return ResourceBundle.getBundle(
                "digital.design.management.system.common.resource.MessageResource",
                locale
        );
    }
}
