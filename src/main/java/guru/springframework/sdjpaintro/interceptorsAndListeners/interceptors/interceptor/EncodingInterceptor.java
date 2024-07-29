package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.interceptor;

import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.encoding.Decoder;
import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.encoding.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EncodingInterceptor implements Interceptor {

    private final Decoder decoder;
    private final Encoder encoder;

    @Autowired
    public EncodingInterceptor(Decoder decoder, Encoder encoder) {
        super();
        this.decoder = decoder;
        this.encoder = encoder;
    }

    // I do not implement any decoding logic in `onLoad` method as the documentation says that an empty uninitialized
    // entity is provided to this method, so the array `Object[] state` has all null values, so there is nothing to 
    // decode. To decode the credit card number @PostLoad annotated method in entity. 
    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        log.info("I am in 'onLoad()' method.");
//        Object[] newState = decoder.applyCoding(entity, propertyNames, state);
        return Interceptor.super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        log.info("I am in 'onFlushDirty()' method.");
        Object[] newState = encoder.applyCoding(entity, propertyNames, currentState);
        return Interceptor.super.onFlushDirty(entity, id, newState, previousState, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        log.info("I am in 'onSave()' method.");
        Object[] newState = encoder.applyCoding(entity, propertyNames, state);
        return Interceptor.super.onSave(entity, id, newState, propertyNames, types);
    }


}
