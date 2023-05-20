package digital.design.management.system.converter;

import digital.design.management.system.enumerate.StatusProject;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class StatusProjectConverter implements AttributeConverter<StatusProject, String> {
    @Override
    public String convertToDatabaseColumn(StatusProject status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
    }

    @Override
    public StatusProject convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }

        return Stream.of(StatusProject.values())
                .filter(c -> c.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

