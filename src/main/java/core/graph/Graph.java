package core.graph;

import core.interfaces.IGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Graph<T> implements IGraph<T> {
    private List<Edge<T>> edges;
    private HashMap<Long, Vertex<T>> cache;
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
        Vertex.reloadStaticField();
        cache = new HashMap<>();
        edges = new ArrayList<>();
    }

    public void setMaxSpanningTreeEdges(List<Edge<T>> edges) {
        this.maxSpanningTreeEdges = edges;
    }

    public void addEdge(T t1, T t2, long weight, long id1, long id2) {
        if (!cache.containsKey(id1)) {
            var v1 = new Vertex<T>(t1);
            v1.setOutId(id1);
            cache.put(id1, v1);
        }
        if (!cache.containsKey(id2)) {
            var v2 = new Vertex<T>(t2);
            v2.setOutId(id2);
            cache.put(id2, v2);
        }
        if (!edgeExists(cache.get(id1), cache.get(id2))) {
            var edge = new Edge<T>(cache.get(id1), cache.get(id2), weight);
            edges.add(edge);
        }
    }

    public void addEdge(T t1, T t2, long weight) {
        if (!cache.containsKey((long)t1.hashCode())) {
            var v1 = new Vertex<T>(t1);
            cache.put((long)t1.hashCode(), v1);
        }
        if (!cache.containsKey((long)t2.hashCode())) {
            var v2 = new Vertex<T>(t2);
            cache.put((long)t2.hashCode(), v2);
        }

        if (!edgeExists(cache.get((long)t1.hashCode()), cache.get((long)t2.hashCode()))) {
            var edge = new Edge<T>(cache.get((long)t1.hashCode()), cache.get((long)t2.hashCode()), weight);
            edges.add(edge);
        }
    }

    private boolean edgeExists(Vertex<T> v1, Vertex<T> v2) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getFirst().hashCode() == v1.hashCode() && edges.get(i).getSecond().hashCode() == v2.hashCode()) {
                return true;
            }
            if (edges.get(i).getFirst().hashCode() == v2.hashCode() && edges.get(i).getSecond().hashCode() == v1.hashCode()) {
                return true;
            }
        }

        return false;
    }
}
