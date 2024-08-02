package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacksAndConverters.converter;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.service.EncodingService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Converter
@Component
@AllArgsConstructor
public class EmployeeEncodingConverter implements AttributeConverter<String, String> {

    private final EncodingService encodingService;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encodingService.encode(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return encodingService.decode(dbData);
    }

}
