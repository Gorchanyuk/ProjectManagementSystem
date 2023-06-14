package digital.design.management.system.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

    public String generate() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
