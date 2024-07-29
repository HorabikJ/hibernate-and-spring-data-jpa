package guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.listener;

import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.encoder.EntityEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PreInsertListener implements PreInsertEventListener {

    private final EntityEncoder encoder;
    
    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        encoder.encode(event.getEntity());
        log.info("PreInsertListener onPreInsert");
        return false;
    }
}
