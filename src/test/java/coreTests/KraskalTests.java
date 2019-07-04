package coreTests;

import core.Kruskal.Edge;
import core.Kruskal.Graph;
import core.Kruskal.Kruskal;
import core.Kruskal.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KraskalTests {
    @Test
    public void Do() {
        Vertex<Integer> v1 = new Vertex<>();
        Vertex<Integer> v2 = new Vertex<>();
        Vertex<Integer> v3 = new Vertex<>();
        Vertex<Integer> v4 = new Vertex<>();
        Vertex<Integer> v5 = new Vertex<>();
        Vertex<Integer> v6 = new Vertex<>();
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(v1, v2, 10);
        graph.addEdge(v2, v3, 11);
        graph.addEdge(v3, v4, 15);
        graph.addEdge(v4, v5, 18);
        graph.addEdge(v5, v6, 9);
        graph.addEdge(v1, v6, 1);
        graph.addEdge(v2, v4, 6);
        graph.addEdge(v2, v5, 12);

        Kruskal<Integer> kruskal = new Kruskal<>(graph);
        kruskal.DoAlg();
        List<Edge<Integer>> edges = graph.getEdges();
        for (Edge e : edges) {
            Logger log = Logger.getLogger(getClass().getName());
            log.log(Level.SEVERE, e.getId() + " " + e.getWeight());
        }

        Assert.assertEquals(5, graph.getEdges().size());
    }
}
