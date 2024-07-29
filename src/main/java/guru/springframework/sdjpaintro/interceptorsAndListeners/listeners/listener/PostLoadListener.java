package guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PostLoadListener implements PostLoadEventListener {

    
    @Override
    public void onPostLoad(PostLoadEvent event) {
        log.info("PostLoadListener onPostLoad");
    }
}
