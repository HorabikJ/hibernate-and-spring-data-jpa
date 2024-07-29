package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.interceptor;


import guru.springframework.sdjpaintro.interceptorsAndListeners.encoding.service.EncodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class Decoder extends TemplateEncoder {

    @Autowired
    public Decoder(EncodingService encodingService) {
        super(encodingService);
    }

    @Override
    void encodeOrDecode(int i, Object[] state) {
        state[i] = encodingService.decode(state[i].toString());
    }

}
