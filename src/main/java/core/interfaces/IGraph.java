package core.interfaces;

import core.graph.Edge;
import core.graph.Vertex;

import java.util.List;

public interface IGraph<T> {
    List<Edge<T>> getEdges();
    void sortEdgesByDesc();
    void setMaxSpanningTreeEdges(List<Edge<T>> edges);
    List<Edge<T>> getMaxSpanningTreeEdges();
    void addEdge(Vertex<T> v1, Vertex<T> v2, long weight);
}
