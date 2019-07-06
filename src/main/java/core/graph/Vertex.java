package core.graph;

public class Vertex<T> {
    private T member;
    private static long ID;
    private long id;

    public Vertex(T t) {
        this.member = t;
        id = ID;
        ID++;
    }

    public long getId() {
        return id;
    }
}
