package mvc.controller;

import com.jfoenix.controls.JFXButton;
import infrastructure.models.PersonContainer;
import infrastructure.rule.Facade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MainController implements Initializable, Runnable {
    private Logger logger = LogManager.getLogger(getClass());
    private PersonContainer myFriends;

    private HashSet<Integer> cacheIndexes = new HashSet<>();

    @FXML
    private AnchorPane friendList;
    @FXML
    private TextFlow mainText;

    @FXML
    private JFXButton showGraphBtn;

    @FXML
    private JFXButton reloadCircle;
    @FXML
    private JFXButton useKruskalBtn;
    @FXML
    private ScrollPane main;
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
        logger.info("getting friends from facade");
        try {
            myFriends = Facade.getInstance().getFriends();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        var thread = new Thread(this);
        thread.run();
    }

    @FXML
    public void createStartGraph(javafx.scene.input.MouseEvent event) {
        logger.info("hash set contains " + cacheIndexes.size());
        if (cacheIndexes.size() <= 1) {
            setPopupMessage("Show graph", "you choose friends from list at first");
            logger.info("hash set is empty, returning ");
            return;
        }
        try {
            var copy  = new PersonContainer();
            copy.setPersons(new ArrayList<>());
            for (int i = 0; i < myFriends.getPersons().size(); i++) {
                if (cacheIndexes.contains(i)) {
                    copy.getPersons().add(myFriends.getPersons().get(i));
                    logger.info(myFriends.getPersons().get(i) + " is included");
                }
            }
//            var g = new GraphNewThread(copy);
//            var t = new Thread(g);
//            t.start();
            createGraph(copy);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void createGraph(PersonContainer copy) {
        try {
            var megaTuple = Facade.getInstance().getStartGraph(copy);
            var graph = new DefaultGraph("MEGAGRAPH", false, true);
            graph.clear();
            for (int i = 0; i < megaTuple.size(); i++) {
                graph.addEdge( i + "", megaTuple.get(i).x, megaTuple.get(i).y ).setAttribute("length",  megaTuple.get(i).z);
                logger.info("EDGE: " + megaTuple.get(i).x + " " + megaTuple.get(i).y  + " " +   megaTuple.get(i).z);
            }
            graph.setAttribute("ui.stylesheet", "edge { size: 3px; fill-color: orange; }");

            int z = 0;

            for (Edge e : graph.getEachEdge()) {
                e.addAttribute("label", "" + (int) e.getNumber("length"));
                // TODO: ITS WOOORK
                e.getNode0().addAttribute("ui.style", "size: 100px; fill-mode: image-scaled; fill-image: url(')');");
                e.getNode0().addAttribute("label", megaTuple.get(z).x);
                e.getNode1().addAttribute("label", megaTuple.get(z).y);
                z++;
            }

            Viewer viewer = graph.display();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void doKruskalAlgorithm(javafx.scene.input.MouseEvent mouseEvent) {
        var copy  = new PersonContainer();
        copy.setPersons(new ArrayList<>());
        logger.info("WHEN I TRY KRUSKAL MY FRIENDS " + myFriends.getPersons().size());
        for (int i = 0; i < myFriends.getPersons().size(); i++) {
            if (cacheIndexes.contains(i)) {
                copy.getPersons().add(myFriends.getPersons().get(i));
                logger.info(myFriends.getPersons().get(i) + " is included");
            }
        }
        if (copy.getPersons().size() <= 1) {
            setPopupMessage("Kruskal`s algorithm", "you create simple graph at first");
            logger.info("myFriends is empty, returning ");
            return;
        }
        try {
            var  megaTuple2 = Facade.getInstance().getMaxTree(copy);
            var graph = new DefaultGraph("MEGAGRAPH", false, true);
            graph = new DefaultGraph("MEGAGRAPH", false, true);
            graph.clear();
            for (int i = 0; i < megaTuple2.size(); i++) {
                graph.addEdge( i + "", megaTuple2.get(i).x, megaTuple2.get(i).y ).setAttribute("length",  megaTuple2.get(i).z);
                logger.info("EDGE: " + megaTuple2.get(i).x + " " + megaTuple2.get(i).y  + " " +   megaTuple2.get(i).z);
            }
            graph.setAttribute("ui.stylesheet", "edge { size: 3px; fill-color: red; }");

            int i = 0;
            for (Edge e : graph.getEachEdge()) {
                e.addAttribute("label", "" + (int) e.getNumber("length"));
                e.getNode0().addAttribute("label", megaTuple2.get(i).x);
                e.getNode0().addAttribute("ui.stylesheet", "node { fill-image: url(''); }");
                e.getNode1().addAttribute("label", megaTuple2.get(i).y);
                e.getNode1().addAttribute("ui.stylesheet", "node {  fill-image: url(''); }");
                i++;
            }

            Viewer viewer = graph.display();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (myFriends == null) {
            try {
                Thread.sleep(300);
                run();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            Node[] nodes = new Node[myFriends.getPersons().size()];
            box.setSpacing(10);
            for (int i = 0; i < nodes.length; i++) {
                final int j = i;
                try {
                    var person = myFriends.getPersons().get(i);
                    Image image = new Image(person.getPhotoUri());
                    // TODO: get out from loop
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Friend.fxml"));
                    AnchorPane rootGroup = loader.load();
                    ImageView r = (ImageView) rootGroup.getChildren().get(0);
                    StackPane rs = (StackPane) rootGroup.getChildren().get(1);
                    r.setImage(image);
                    Label text = new Label(person.getFirstName() + " " + person.getLastName());
                    text.setAlignment(Pos.CENTER);
                    rs.getChildren().add(text);
                    rootGroup.getChildren().clear();
                    rootGroup.getChildren().add(r);
                    rootGroup.getChildren().add(rs);

                    nodes[i] = rootGroup;
                    nodes[i].setOnMouseClicked(event -> {
                        System.out.println(j);
                        if (cacheIndexes.contains(j)) {
                            nodes[j].setStyle("-fx-background-color: #b085f5");
                            cacheIndexes.remove(j);
                        } else {
                            cacheIndexes.add(j);
                            nodes[j].setStyle("-fx-background-color: #e91e63");
                        }
                    });
                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color: #ee98fb;");
                    });

                    nodes[i].setOnMouseExited(event -> {
                        if (!cacheIndexes.contains(j)) {
                            nodes[j].setStyle("-fx-background-color: #b085f5");
                        } else {
                            nodes[j].setStyle("-fx-background-color: #e91e63");
                        }
                    });

                    box.getChildren().add(nodes[i]);
                    System.out.println("add node");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }

            logger.info("end node");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void reloadScene(MouseEvent mouseEvent) {
        setGreetingMessage();
    }
}
// TODO: maybe later add new thread
//class GraphNewThread implements Runnable {
//    PersonContainer copy;
//    public GraphNewThread(PersonContainer container) {
//        this.copy = container;
//    }
//    private void createGraph() {
//        System.out.println("I AM IN NEW THREAD " + Thread.currentThread().getId());
//        try {
//            var megaTuple = Facade.getInstance().getStartGraph(copy);
//            var graph = new DefaultGraph("MEGAGRAPH", false, true);
//            graph.clear();
//            for (int i = 0; i < megaTuple.size(); i++) {
//                graph.addEdge( i + "", megaTuple.get(i).x, megaTuple.get(i).y ).setAttribute("length",  megaTuple.get(i).z);
//            }
//            graph.setAttribute("ui.stylesheet", "edge { size: 3px; fill-color: orange; }");
//
//            int z = 0;
//            for (Edge e : graph.getEachEdge()) {
//                e.addAttribute("label", "" + (int) e.getNumber("length"));
//                e.getNode0().addAttribute("label", megaTuple.get(z).x);
//                e.getNode1().addAttribute("label", megaTuple.get(z).y);
//                z++;
//            }
//            Viewer viewer = graph.display();
//            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public void run() {
//        createGraph();
//    }
//}