package core.Kruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph<T> {
    private List<Edge<T>> edges;

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public Graph() {
        edges = new ArrayList<>();
    }

    public void changeEdges(List<Edge<T>> edges) {
        this.edges = edges;
    }
    public void addEdge(Vertex<T> v1, Vertex<T> v2, long weight) {
        Edge<T> edge = new Edge<T>(v1, v2, weight);
        edges.add(edge);
    }

    public void getSortedEdges() {
        Collections.sort(edges, Collections.reverseOrder());
//        edges.sort(Collections.reverseOrder());
    }

    public void removeMin(Edge<T> e) {
        edges.remove(e);
    }
}
