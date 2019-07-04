package core.Kruskal;

import java.util.ArrayList;
import java.util.List;

public class Kruskal<T> {
    private Graph<T> graph;
    public Kruskal(Graph<T> graph) {
        this.graph = graph;
    }

    public void DoAlg() {
        graph.getSortedEdges();
        DisjointSet disjointSet = new DisjointSet(graph.getEdges().size());

        int ret = 0;
//        for (int i = 0; i < graph.getEdges().size(); i++) {
//            Edge<T> cur = graph.getEdges().get(i);
//            if (!disjointSet.union((Vertex<T>)e.getFirst().getId(), e.getSecond().hashCode())) {
//                graph.removeMin(e);
//            }
//        }
        List<Edge<T>> edges = new ArrayList<>();
        for (Edge e : graph.getEdges()) {
            if (disjointSet.union((int)e.getFirst().getId(), (int)e.getSecond().getId())) {
//                graph.removeMin(e);
                edges.add(e);
            }
        }
        graph.changeEdges(edges);
    }
}
