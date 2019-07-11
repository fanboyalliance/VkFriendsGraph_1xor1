package core.graph;

import java.util.Objects;

public class Vertex<T> {
    private T member;
    public static long ID;
    private long id;
    private long outId;

    public Vertex(T t) {
        this.member = t;
        id = ID;
        ID++;
    }

    public static void reloadStaticField() {
        ID = 0;
    }

    public void setOutId(long outId) {
        this.outId = outId;
    }
    public long getOutId() {
        return this.outId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(member, vertex.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member);
    }

    public long getId() {
        return id;
    }
}
