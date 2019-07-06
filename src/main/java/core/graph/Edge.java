package core.graph;

public class Edge<T> implements Comparable<Edge<T>>{
    private Vertex<T> first;
    private Vertex<T> second;
    private long weight;
    private static long ID;
    private long id;

    public Edge(Vertex<T> v1, Vertex<T> v2, long weight) {
        this.first = v1;
        this.second = v2;
        this.weight = weight;
        id = ID;
        ID++;
    }

    public Vertex<T> getFirst() {
        return first;
    }

    public Vertex<T> getSecond() {
        return second;
    }

    public long getId() {
        return id;
    }

    public long getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge<T> o) {
        if (this.getWeight() != o.getWeight()) return this.getWeight() < o.getWeight() ? -1 : 1;
        return 0;
    }
}
