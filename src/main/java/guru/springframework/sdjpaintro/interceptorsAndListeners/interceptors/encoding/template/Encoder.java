package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.encoding.template;

import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.encoding.service.EncodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Encoder extends TemplateEncoder {

    @Autowired
    public Encoder(EncodingService encodingService) {
        super(encodingService);
    }

    @Override
    void encodeOrDecode(int i, Object[] state) {
        state[i] = encodingService.encode(state[i].toString());
    }

}
