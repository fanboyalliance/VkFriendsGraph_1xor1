package mvc.controller;

import com.jfoenix.controls.JFXButton;
import infrastructure.interfaces.IGuiConnector;
import infrastructure.models.MinPersonDTO;
import infrastructure.logic.GuiConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import mvc.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MainController implements Initializable, Runnable {
    private Logger logger;
    private IGuiConnector guiConnector;
    private ArrayList<Person> myFriends;
    private HashSet<Integer> indexesCache;
    private static HashMap<Long, Person> personCache;
    private ModelFiller modelFiller;

    public MainController() {
        logger = LogManager.getLogger(getClass());
        guiConnector = new GuiConnector();
        indexesCache = new HashSet<>();
        personCache = new HashMap<>();
        modelFiller = new ModelFiller();
    }

    @FXML
    private TextFlow mainText;

    @FXML
    private VBox box;

    private void setGreetingMessage() {
        mainText.getChildren().clear();
        var t = new Text("\n Hello!\n\n You can choose your friends from the right list " +
                "\n\n  Create a graph of the mutual friends of the friends you picked\n\n " +
                "And built maximum spanning tree of this graph with Kruskal`s\n\n algorithm");
        t.getStyleClass().add("greetingText");
        mainText.getChildren().add(t);
    }

    private void setPopupMessage(String button, String condition) {
        mainText.getChildren().clear();
        var t = new Text("\n\n\n\n You can use " + button + " if " + condition + "\n\n We apologize!");
        t.getStyleClass().add("greetingText");
        mainText.getChildren().add(t);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setGreetingMessage();
        try {
            myFriends = modelFiller.getPersonModel(guiConnector.getFriends());
            for (var p : myFriends) {
                personCache.put(p.id, p);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        var thread = new Thread(this);
        thread.run();
    }

    @FXML
    public void createGraphEvent(javafx.scene.input.MouseEvent event) {
        logger.info("HashSet contains " + indexesCache.size() + " indexes");
        if (indexesCache.size() <= 1) {
            setPopupMessage("Show graph", "you choose friends from list at first");
            logger.info("hash set is empty, returning ");

            return;
        }
        try {
            var copy  = new ArrayList<MinPersonDTO>();
            ArrayList<MinPersonDTO> minDto = modelFiller.getMinDto(myFriends);
            for (int i = 0; i < myFriends.size(); i++) {
                if (indexesCache.contains(i)) {
                    copy.add(minDto.get(i));
                    logger.info(myFriends.get(i).firstName + " " + myFriends.get(i).lastName + " is included");
                }
            }

            createGraph(copy);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void doKruskalEvent(javafx.scene.input.MouseEvent mouseEvent) {
        logger.info("HashSet contains " + indexesCache.size() + " indexes");
        if (indexesCache.size() <= 1) {
            setPopupMessage("Kruskal`s algorithm", "you create simple graph at first");
            logger.info("myFriends is empty, returning ");

            return;
        }
        try {
            doKruskal();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void createGraph(ArrayList<MinPersonDTO> copy) {
        try {
            var thread = new Thread(() -> {

                ArrayList<MinPersonDTO> minPersonDto = null;
                try {
                    minPersonDto = guiConnector.getStartGraph(copy);
                    displayGraph(minPersonDto, "edge { size: 3px; fill-color: orange; text-size: 20; }");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            });

            thread.start();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void doKruskal() {
        try {
            var thread = new Thread(() -> {
                ArrayList<MinPersonDTO> minPersonDto = null;
                try {
                    minPersonDto = guiConnector.getMaxTree();
                    displayGraph(minPersonDto, "edge { size: 3px; fill-color: red; text-size: 20; }");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            });

            thread.start();

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayGraph(ArrayList<MinPersonDTO> minPersonDto, String graphStyle) {
        var persons = modelFiller.getMutualFriends(myFriends, minPersonDto);
        var graph = new DefaultGraph("MEGAGRAPH", false, true);
        graph.clear();
        for (int i = 0; i < persons.size(); i++) {
            graph.addEdge( i + "", persons.get(i).id + "", persons.get(i).friendId + "" ).setAttribute("length",  persons.get(i).mutualFriendsCount);
            logger.info("EDGE: " + persons.get(i).id + " " + persons.get(i).friendId  + " " +   persons.get(i).mutualFriendsCount);
        }
        graph.setAttribute("ui.stylesheet", graphStyle);
        int i = 0;
        for (Edge e : graph.getEachEdge()) {
            e.addAttribute("label", "" + (int) e.getNumber("length"));
            e.getNode0().addAttribute("ui.style", "size: 100px; fill-mode: image-scaled; fill-image: url('" + persons.get(i).photoUri + ")');");
            var secondPerson = personCache.get(persons.get(i).friendId);
            e.getNode1().addAttribute("ui.style", "size: 100px; fill-mode: image-scaled; fill-image: url('" + secondPerson.photoUri + ")');");
            i++;
        }

        var viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }


    @Override
    public void run() {
        if (myFriends == null) {
            try {
                Thread.sleep(300);
                run();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        try {
            Node[] nodes = new Node[myFriends.size()];
            box.setSpacing(10);
            for (int i = 0; i < nodes.length; i++) {
                final int j = i;
                try {
                    var person = myFriends.get(i);
                    Image image = new Image(person.photoUri);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Friend.fxml"));
                    AnchorPane rootGroup = loader.load();
                    ImageView imageView = (ImageView) rootGroup.getChildren().get(0);
                    StackPane stackPane = (StackPane) rootGroup.getChildren().get(1);
                    imageView.setImage(image);
                    var text = new Label(person.firstName  + " " + person.lastName);
                    text.getStyleClass().add("friend-txt");
                    text.setAlignment(Pos.CENTER);
                    stackPane.getChildren().add(text);
                    rootGroup.getChildren().clear();
                    rootGroup.getChildren().add(imageView);
                    rootGroup.getChildren().add(stackPane);

                    nodes[i] = rootGroup;
                    nodes[i].setOnMouseClicked(event -> {
                        if (indexesCache.contains(j)) {
                            nodes[j].setStyle("-fx-background-color: #b085f5");
                            indexesCache.remove(j);
                            logger.info("User with position " + j + " in the right list was deleted from cache");
                        } else {
                            indexesCache.add(j);
                            logger.info("User with position " + j + " in the right list was added to cache");
                            nodes[j].setStyle("-fx-background-color: #e91e63");
                        }
                    });
                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color: #ee98fb;");
                    });

                    nodes[i].setOnMouseExited(event -> {
                        if (!indexesCache.contains(j)) {
                            nodes[j].setStyle("-fx-background-color: #b085f5");
                        } else {
                            nodes[j].setStyle("-fx-background-color: #e91e63");
                        }
                    });

                    box.getChildren().add(nodes[i]);
                    logger.info("add node");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void reloadScene(MouseEvent mouseEvent) {
        setGreetingMessage();
        indexesCache.clear();
        for (var ch : box.getChildren()) {
            ch.setStyle("-fx-background-color: #b085f5");
        }
    }
}