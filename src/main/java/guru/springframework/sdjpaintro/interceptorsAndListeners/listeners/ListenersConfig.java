package guru.springframework.sdjpaintro.interceptorsAndListeners.listeners;

import guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.listener.PostLoadListener;
import guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.listener.PreInsertListener;
import guru.springframework.sdjpaintro.interceptorsAndListeners.listeners.listener.PreUpdateListener;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ListenersConfig {

//    https://stackoverflow.com/questions/55731263/spring-boot-2-1-2-hibernate-5-register-hibernate-event-listeners-insert-updat

    private final EntityManagerFactory entityManagerFactory;
    private final PostLoadListener postLoadListener;
    private final PreInsertListener preInsertListener;
    private final PreUpdateListener preUpdateListener;

    @PostConstruct
    public void init() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry eventListenerRegistry = sessionFactory
                .getServiceRegistry()
                .getService(EventListenerRegistry.class);

        eventListenerRegistry.appendListeners(EventType.POST_LOAD, postLoadListener);
        eventListenerRegistry.appendListeners(EventType.PRE_INSERT, preInsertListener);
        eventListenerRegistry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
    }


}
