package guru.springframework.sdjpaintro.interceptorsAndListeners.interceptors.encoding;

public interface EncodingService {

    String encode(String data);

    String decode(String data);

}