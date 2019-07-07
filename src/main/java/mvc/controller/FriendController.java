package mvc.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendController extends AnchorPane implements Initializable {
    @FXML
    private ImageView personPhoto;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane info;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
