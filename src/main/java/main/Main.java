package main;
import com.google.inject.Guice;
import com.google.inject.Injector;
import infrastructure.Queries.DoRequests;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application{
    public static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String VK_AUTH_URL = ""; //TODO!!!
    public static String tokenUrl;

    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new BasicModule());
        DoRequests s = injector.getInstance(DoRequests.class);
        s.DoSmth();
        DoRequests d = new DoRequests();
        d.DoSmth();
        launch(args);
    }

    @Override
    public void start(Stage ps) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Weq.fxml"));
        ps.setTitle("LOL");
        ps.setScene(new Scene(root));
        ps.show();
    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        final WebView view = new WebView();
//        final WebEngine engine = view.getEngine();
//        engine.load(VK_AUTH_URL);
//
//
//        primaryStage.setScene(new Scene(view));
//        primaryStage.show();
//
//        engine.locationProperty().addListener(new ChangeListener<String>(){
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if(newValue.startsWith(REDIRECT_URL)){
//                    tokenUrl=newValue;
//                    primaryStage.close();
//                }
//            }
//
//        });
//
//    }
}
