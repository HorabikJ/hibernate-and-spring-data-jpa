package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.encoder;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.annotations.EncodedString;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.service.EncodingService;
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

    public void encode(Object[] state, String[] propertyNames, Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(),
                field -> encodeField(field, state, propertyNames),
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

    private void encodeField(Field field, Object[] state, String[] propertyNames) {
        int idx = getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[idx];
        state[idx] = encodingService.encode(currentValue.toString());
    }

    private int getPropertyIndex(String name, String[] properties) {
        for (int i = 0; i < properties.length; i++) {
            if (name.equals(properties[i])) {
                return i;
            }
        }
        //should never get here
        throw new IllegalArgumentException("Property not found: " + name);
    }


}
