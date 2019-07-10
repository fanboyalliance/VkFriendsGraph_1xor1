package infrastructure.Edges;

import core.graph.Edge;
import core.graph.Graph;
import core.interfaces.IGraph;
import core.interfaces.IKruskal;
import core.kruskal.Kruskal;
import infrastructure.interfaces.IFriendGraphGetter;
import infrastructure.models.MinPersonDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FriendGraphGetter implements IFriendGraphGetter {
    private Logger logger;
    private IGraph<Long> graph;
    private IKruskal<Long> kruskal;

    public FriendGraphGetter() {
        logger = LogManager.getLogger(getClass());
        this.kruskal = new Kruskal<>();
    }

    public ArrayList<MinPersonDTO> getEdgesForStartGraph(@NotNull HashMap<Long, ArrayList<MinPersonDTO>> friendsByPersonId) {
        this.graph = new Graph<>();
        for(Map.Entry<Long,  ArrayList<MinPersonDTO>> entry : friendsByPersonId.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                graph.addEdge(entry.getKey(), entry.getValue().get(i).friendId, entry.getValue().get(i).weight, entry.getKey(), entry.getValue().get(i).friendId);
            }
        }

        var edges = graph.getEdges();
        var answer = new ArrayList<MinPersonDTO>();
        for (Edge<Long> edge : edges) {
            var minPerson = new MinPersonDTO();
            minPerson.id =  edge.getFirst().getOutId();
            minPerson.friendId = edge.getSecond().getOutId();
            minPerson.weight = edge.getWeight();
            answer.add(minPerson);
        }

        return answer;
    }

    public ArrayList<MinPersonDTO> getEdgesForMinTree(@NotNull HashMap<Long, ArrayList<MinPersonDTO>> friendsByPersonId) {
        this.graph = new Graph<>();
        for(Map.Entry<Long, ArrayList<MinPersonDTO>> entry : friendsByPersonId.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                graph.addEdge(entry.getKey(), entry.getValue().get(i).friendId, entry.getValue().get(i).weight, entry.getKey(), entry.getValue().get(i).friendId);
            }
        }

        var edges = graph.getEdges();
        kruskal.findMaxTree(graph);
        edges = graph.getMaxSpanningTreeEdges();
        var answer = new ArrayList<MinPersonDTO>();
        for (Edge<Long> edge : edges) {
            var minPerson = new MinPersonDTO();
            minPerson.id =  edge.getFirst().getOutId();
            minPerson.friendId = edge.getSecond().getOutId();
            minPerson.weight = edge.getWeight();
            answer.add(minPerson);
        }

        return answer;
    }
}
