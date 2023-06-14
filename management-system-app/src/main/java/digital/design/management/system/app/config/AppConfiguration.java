package digital.design.management.system.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
@ComponentScan("digital.design.management.system")
public class AppConfiguration {

    @Bean
    public PasswordEncoder devPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public ResourceBundle getResourceBundle() {
        Locale locale =
                new Locale.Builder()
                        .setLanguage("ru")
                        .setRegion("RU")
                        .build();

        return ResourceBundle.getBundle(
                "message_resource",
                locale
        );
    }


}
