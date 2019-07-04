package infrastructure.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class DoRequests {
    private static HttpURLConnection con;

    public void DoSmth() throws IOException {
        String url = "https://api.vk.com/method/friends.get?user_id=439893478&fields=photo_100,online&access_token=b31ff5f81a44e8e8722ae2554beaa52f714a7c6f29c96a741ea9949f8b48aa9bf405d0b78af3c1f08f1df&v=5.101";
        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

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

            System.out.println(content.toString());

        } finally {

            con.disconnect();
        }
    }
}
