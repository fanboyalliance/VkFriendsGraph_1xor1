package mvc.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private HashSet<Integer> nodesIndex = new HashSet<>();
    @FXML
    private AnchorPane friendList;

    @FXML
    private ScrollPane main;

    @FXML
    private VBox box;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[27];
        var url =  "";
        Image image = new Image(url);
        box.setSpacing(10);
        for (int i = 0; i < nodes.length; i++) {
            final int j = i;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Friend.fxml"));
                AnchorPane rootGroup = loader.load();
                ImageView r = (ImageView) rootGroup.getChildren().get(0);
                StackPane rs = (StackPane) rootGroup.getChildren().get(1);
                r.setImage(image);
//                r.setFitHeight(100);
//                r.setFitWidth(100);
                Label text = new Label("test");
                text.setAlignment(Pos.CENTER);
                rs.getChildren().add(text);
//                rs.setPrefHeight(100);
//                rs.setPrefWidth(252);
                rootGroup.getChildren().clear();
                rootGroup.getChildren().add(r);
                rootGroup.getChildren().add(rs);

//                friendList.getChildren().add(rootGroup);
//                var url =  "https://i.stack.imgur.com/mQGxS.png";
//                Image image = new Image(url);
//                FriendController friendController = loader.getController();
//                friendController.setImage(image);
//                loader.setController(friendController);
//                loader.setLocation(getClass().getResource("/fxml/FriendController.fxml"));
//                friendController = loader.getController();

//                friendController.setImage(image);
//                friendController.setPersonInfo("ITS INFO OFAOFSOSFAOOFASOF");
//                nodes[i] = friendController;
//                rootGroup.getChildren().clear();
//                rootGroup.getChildren().add(friendController);
//                rootGroup.setPrefWidth(352);
//                rootGroup.setPrefHeight(135);
                nodes[i] = rootGroup;
                nodes[i].setOnMouseClicked(event -> {
                    System.out.println(j);
                    var method = box.getChildren().get(j);
                    if (nodesIndex.contains(j)) {
                        nodes[j].setStyle("-fx-background-color: orange");
                        nodesIndex.remove(j);
                    } else {
                        nodesIndex.add(j);
                        nodes[j].setStyle("-fx-background-color: blue");
                    }
                });
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color: red;");
                });

                nodes[i].setOnMouseExited(event -> {
                    if (!nodesIndex.contains(j)) {
                        nodes[j].setStyle("-fx-background-color: orange");
                    } else {
                        nodes[j].setStyle("-fx-background-color: blue");
                    }
                });


                box.getChildren().add(nodes[i]);
                System.out.println("add node");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
        System.out.println("end node");
//        for (int i = 0; i < nodes.length; i++) {
//            try {
//                AnchorPane loader = FXMLLoader.load(getClass().getResource("/fxml/FriendController.fxml"));
//                var friend =  loader.getChildren();
//                FXMLLoader loader2 = new FXMLLoader();
//                FriendController friendController = new FriendController();
//                var url =  "https://i.stack.imgur.com/mQGxS.png";
//                Image image = new Image(url);
//                friendController.setImage(image);
//                friendController.setPersonInfo("ITS INFO OFAOFSOSFAOOFASOF");
//                loader2.setController(friendController);
////                nodes[i] = loader.load();
////                var f = nodes[i].getProperties();
////                nodes[i].getProperties().put("personPhoto", image);
//                box.getChildren().add(nodes[i]);
//                System.out.println("add node");
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
    }
    @FXML
    private ImageView imageView;

    @FXML
    public void doSmth(javafx.scene.input.MouseEvent event) {
        System.out.println("click");
//        Image image = null;
        try {
            // var url =  "https:\\/\\/pp.userapi.com\\/c844321\\/v844321442\\/1a266d\\/VvYmH-YOQmw.jpg?ava=1";
            var url =  "https://i.stack.imgur.com/mQGxS.png";
//            var regex = "(\\\\/)";
//            url = url.replaceAll(regex, "/");
            Image image = new Image(url);
            imageView.setImage(image);
        } finally {

        }


//        Image image = new Image("https:\\/\\/pp.userapi.com\\/c844321\\/v844321442\\/1a266d\\/VvYmH-YOQmw.jpg?ava=1");
    }
}
