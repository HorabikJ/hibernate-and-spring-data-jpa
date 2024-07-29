package guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.encoder;

import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.annotations.EncodedString;
import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.service.EncodingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@AllArgsConstructor
@Component
@Slf4j
public class EntityEncoder {

    private final EncodingService encodingService;

    public void encode(Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(),
                field -> encodeField(field, entity),
                this::isFieldAnnotatedWithEncodedString);
    }

    public void decode(Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(),
                field -> decodeField(field, entity),
                this::isFieldAnnotatedWithEncodedString);
    }

    private boolean isFieldAnnotatedWithEncodedString(Field field) {
        return AnnotationUtils.findAnnotation(field, EncodedString.class) != null;
    }

    private void decodeField(Field field, Object entity) {
        field.setAccessible(true);
        Object value = ReflectionUtils.getField(field, entity);
        if (value != null) {
            String decoded = encodingService.decode(value.toString());
            log.info("Decoding field [{}] from value [{}] to value [{}] in class [{}]",
                    field.getName(),
                    value,
                    decoded,
                    entity.getClass().getSimpleName());
            ReflectionUtils.setField(field, entity, decoded);
        }
    }

    private void encodeField(Field field, Object entity) {
        field.setAccessible(true);
        Object value = ReflectionUtils.getField(field, entity);
        if (value != null) {
            String encoded = encodingService.encode(value.toString());
            log.info("Encoding field [{}] from value [{}] to value [{}] in class [{}]",
                    field.getName(),
                    value,
                    encoded,
                    entity.getClass().getSimpleName());
            ReflectionUtils.setField(field, entity, encodingService.encode(value.toString()));
        }
    }

}
