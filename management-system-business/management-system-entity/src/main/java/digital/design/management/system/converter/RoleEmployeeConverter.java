package digital.design.management.system.converter;

import digital.design.management.system.enumerate.RoleEmployee;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class RoleEmployeeConverter implements AttributeConverter<RoleEmployee, String> {

    @Override
    public String convertToDatabaseColumn(RoleEmployee role) {
        if (role == null) {
            return null;
        }
        return role.getStatus();
    }

    @Override
    public RoleEmployee convertToEntityAttribute(String role) {
        if (role == null) {
            return null;
        }

        return Stream.of(RoleEmployee.values())
                .filter(c -> c.getStatus().equals(role))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
