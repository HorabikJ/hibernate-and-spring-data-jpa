package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.interceptor;

import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.service.EncodingService;
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
