package core.kruskal;

import core.interfaces.IGraph;
import core.interfaces.IKruskal;
import core.graph.Edge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Kruskal<T> implements IKruskal<T> {
    private IGraph<T> graph;
    private DisjointSet disjointSet;
    private Logger logger;

    public Kruskal(IGraph<T> graph) {
        this.graph = graph;
        this.disjointSet = new DisjointSet(graph.getEdges().size());
        logger = LogManager.getLogger(getClass());
    }

    public void findMaxTree() {
        graph.sortEdgesByDesc();
        logger.info("Start to do kruskal algorithm. Graph has " + graph.getEdges().size() + " sorted edges");

        List<Edge<T>> edges = new ArrayList<>();
        try {
            for (Edge e : graph.getEdges()) {
                if (disjointSet.union((int)e.getFirst().getId(), (int)e.getSecond().getId())) {
                    logger.info("Edge " + e.getId() + " is added to answer");
                    edges.add(e);
                } else  {
                    logger.info("Edge " + e.getId() + " is skipped coz it creates a loop");
                }
            }
        } catch (Exception e) {
            logger.error("Algorithm cannot be done");
            logger.error(e.getMessage());
        }

        logger.info("Maximum spanning tree of this graph has " + edges.size() + " edges");
        graph.setMaxSpanningTreeEdges(edges);
    }
}
