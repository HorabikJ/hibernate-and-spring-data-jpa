package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.interceptors.interceptor;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.service.EncodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class Encoder extends TemplateEncoder {

    @Autowired
    public Encoder(EncodingService encodingService) {
        super(encodingService);
    }

    @Override
    void encodeOrDecode(int i, Object[] state) {
        state[i] = encodingService.encode(state[i].toString());
    }

}
