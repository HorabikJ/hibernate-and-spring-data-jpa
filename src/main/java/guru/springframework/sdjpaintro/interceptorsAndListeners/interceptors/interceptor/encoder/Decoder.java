package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.interceptor.encoder;


import guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.encoding.EncodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Decoder extends TemplateEncoder {

    @Autowired
    public Decoder(EncodingService encodingService) {
        super(encodingService);
    }

    @Override
    void encodeOrDecode(int i, Object[] state) {
        state[i] = encodingService.decode(state[i].toString());
    }

}
