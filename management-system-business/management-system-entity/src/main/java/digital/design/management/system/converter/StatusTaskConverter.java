package digital.design.management.system.converter;

import digital.design.management.system.enumerate.StatusTask;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class StatusTaskConverter implements AttributeConverter<StatusTask, String> {
    @Override
    public String convertToDatabaseColumn(StatusTask status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
    }

    @Override
    public StatusTask convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }

        return Stream.of(StatusTask.values())
                .filter(c -> c.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

