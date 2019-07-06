package core.graph;

import core.interfaces.IGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph<T> implements IGraph<T> {
    private List<Edge<T>> edges;
    private List<Edge<T>> maxSpanningTreeEdges;

    public List<Edge<T>> getMaxSpanningTreeEdges() {
        return maxSpanningTreeEdges;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public void sortEdgesByDesc() {
        edges.sort(Collections.reverseOrder());
    }

    public Graph() {
        edges = new ArrayList<>();
    }

    public void setMaxSpanningTreeEdges(List<Edge<T>> edges) {
        this.maxSpanningTreeEdges = edges;
    }

    public void addEdge(T t1, T t2, long weight) {
        Vertex<T> v1 = new Vertex<>(t1);
        Vertex<T> v2 = new Vertex<>(t2);
        Edge<T> edge = new Edge<T>(v1, v2, weight);
        edges.add(edge);
    }
    public void addEdge(Vertex<T> v1, Vertex<T> v2, long weight) {
        Edge<T> edge = new Edge<T>(v1, v2, weight);
        edges.add(edge);
    }
}
