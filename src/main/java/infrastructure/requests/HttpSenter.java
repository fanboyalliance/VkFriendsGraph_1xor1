package infrastructure.requests;

import infrastructure.interfaces.IHttpSenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSenter implements IHttpSenter {
    private Logger logger;
    private HttpURLConnection con;

    public HttpSenter() {
        logger = LogManager.getLogger(getClass());
    }

    public String getRequest(String uri) throws IOException {
        logger.info("Get request by uri " + uri);
        String response = null;
        try {
            var url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            response = content.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new IOException();
        } finally {
            con.disconnect();
        }
        logger.info("Success");

        return response;
    }
}
