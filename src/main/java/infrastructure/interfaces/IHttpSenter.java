package infrastructure.interfaces;

import java.io.IOException;

public interface IHttpSenter {
    String getRequest(String uri) throws IOException;
}
