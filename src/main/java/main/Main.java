package main;
import infrastructure.config.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.regex.Pattern;

public class Main extends Application{
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            new ConfigReader().read();
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.exit(-1);
        }

        logger.error("Thread id from main " + Thread.currentThread().getId());
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        launch(args);
    }

    @Override
    public void start(Stage ps) throws Exception {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        logger.info("start load");
        webEngine.load(getFirstLoadUrl());

        ps.setScene(new Scene(webView));
        ps.show();
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(Config.REDIRECT_URI)) {
                logger.info(newValue);
                setApiToken(newValue);
                setUserId(newValue);

                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    return;
                }

                ps.setTitle("LOL");
                ps.resizableProperty().setValue(Boolean.FALSE);
                ps.show();
                ps.setScene(new Scene(root));
            }
        });
    }

    private String getFirstLoadUrl() {
        var sb = new StringBuilder();
        sb.append(Config.OAUTH_BASE_POINT);
        sb.append("authorize?");
        sb.append("client_id=");
        sb.append(Config.CLIENT_ID);
        sb.append("&display=page");
        sb.append("&redirect_uri=");
        sb.append(Config.REDIRECT_URI);
        sb.append("&scope=friends");
        sb.append("&response_type=token");
        sb.append("&v=");
        sb.append(Config.VERSION);

        return sb.toString();
    }

    private void setApiToken(String newValue) {
        var token = "access_token=";
        var regex = "((access_token=)\\w+)";
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(newValue);
        if (matcher.find()) {
            Config.API_TOKEN = matcher.group(0).substring(token.length());
            logger.info("token " + Config.API_TOKEN);
        }
    }

    private void setUserId(String newValue) {
        var user_id = "user_id=";
        var regex = "((user_id=)\\d+)";
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(newValue);
        if (matcher.find()) {
            Config.USER_ID = Long.parseLong(matcher.group(0).substring(user_id.length()));
            logger.info("userId " + Config.USER_ID);
        }
    }
}
