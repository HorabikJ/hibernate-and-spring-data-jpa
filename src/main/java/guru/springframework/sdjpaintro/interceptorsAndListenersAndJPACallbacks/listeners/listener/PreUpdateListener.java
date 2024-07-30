package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.listeners.listener;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.encoder.EntityEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PreUpdateListener implements PreUpdateEventListener {

    private final EntityEncoder encoder;

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        encoder.encode(event.getState(), event.getPersister().getPropertyNames(), event.getEntity());
        log.info("PreUpdateListener onPreUpdate");
        return false;
    }

}
