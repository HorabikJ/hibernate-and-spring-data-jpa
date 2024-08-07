package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.interceptors;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.interceptors.interceptor.EncodingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class InterceptorsConfig implements HibernatePropertiesCustomizer {

    @Autowired
    private EncodingInterceptor encodingInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", encodingInterceptor);
    }

}
