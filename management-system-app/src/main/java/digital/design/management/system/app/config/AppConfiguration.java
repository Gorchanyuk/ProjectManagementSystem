package digital.design.management.system.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
public class AppConfiguration {

    @Bean
    public PasswordEncoder devPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
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
