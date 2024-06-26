package guru.springframework.sdjpaintro.interceptorsAndListeners.encoding;


import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
public class Base64EncodingService implements EncodingService {

    @Override
    public String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decode(String data) {
        return Arrays.toString(Base64.getDecoder().decode(data));
    }

}
