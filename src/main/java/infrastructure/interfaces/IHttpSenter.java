package infrastructure.interfaces;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface IHttpSenter {
    String getRequest(@NotNull String uri) throws IOException;
}
