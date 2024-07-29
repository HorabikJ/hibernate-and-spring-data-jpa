package guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.listener;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PreUpdateListener implements PreUpdateEventListener {

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        log.info("PreUpdateListener onPreUpdate");
        return false;
    }

}
