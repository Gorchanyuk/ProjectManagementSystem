package digital.design.management.system.converter;


import digital.design.management.system.enumerate.StatusEmployee;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class StatusEmployeeConverter implements AttributeConverter<StatusEmployee, String> {
    @Override
    public String convertToDatabaseColumn(StatusEmployee status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
    }

    @Override
    public StatusEmployee convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }

        return Stream.of(StatusEmployee.values())
                .filter(c -> c.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
