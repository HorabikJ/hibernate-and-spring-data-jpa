package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.encoding.template;

import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.encoding.service.EncodingService;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


abstract class TemplateEncoder {

    protected final EncodingService encodingService;

    public TemplateEncoder(EncodingService encodingService) {
        this.encodingService = encodingService;
    }

    public Object[] applyCoding(Object entity, String[] propertyNames, Object[] state) {
        for (Field field : getAnnotatedFields(entity)) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(field.getName())) {
                    encodeOrDecode(i, state);
                }
            }
        }
        return Arrays.copyOf(state, state.length);
    }

    abstract void encodeOrDecode(int i, Object[] state);

    private Set<Field> getAnnotatedFields(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(EncodedString.class))
                .collect(Collectors.toSet());
    }

}
