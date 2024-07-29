package guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.listener;

import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.encoder.EntityEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PostLoadListener implements PostLoadEventListener {

    private final EntityEncoder encoder;

    @Override
    public void onPostLoad(PostLoadEvent event) {
        encoder.decode(event.getEntity());
        log.info("PostLoadListener onPostLoad");
    }
}
