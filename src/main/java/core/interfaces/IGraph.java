package core.interfaces;

import core.graph.Edge;
import core.graph.Vertex;

import java.util.List;

public interface IGraph<T> {
    List<Edge<T>> getEdges();
    void sortEdgesByDesc();
    void setMaxSpanningTreeEdges(List<Edge<T>> edges);
    List<Edge<T>> getMaxSpanningTreeEdges();
    void addEdge(T t1, T t2, long weight, long id1, long id2);
    void addEdge(T t1, T t2, long weight);
}
