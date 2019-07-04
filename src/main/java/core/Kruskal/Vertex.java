package core.Kruskal;

import java.util.Objects;

public class Vertex<T> {
    private T member;
    private static long ID;
    private long id;

    public Vertex() {
        id = ID;
        ID++;
    }

    public long getId() {
        return id;
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
        return Objects.hash(member, id);
    }
}
