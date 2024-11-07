package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vertex<T> {
    final T contents;
    final List<Edge<T>> edges;

    public Vertex(T item) {
        this.contents = item;
        this.edges = new ArrayList<>();
    }

    public Edge<T> link(Vertex<T> other, Relationship relationship) {
        return new Edge<>(this, other, relationship);
    }

    public T getContents() {
        return contents;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return "Vertex{ " + contents + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex<?> vertex = (Vertex<?>) o;

        return contents.equals(vertex.contents);
    }

    @Override
    public int hashCode() {
        return contents.hashCode();
    }

    public Map<Vertex<T>, Relationship> getAdjacent() {
        HashMap<Vertex<T>, Relationship> ret = new HashMap<>();
        this.getEdges().forEach(
                edge -> {
                    if (edge.a == this) {
                        ret.put(edge.b, edge.relationship);
                    } else {
                        ret.put(edge.a, edge.relationship.reverse());
                    }
                }
        );
        return ret;
    }
}
