package graph;

public interface Constraint<T> {
    boolean test(Edge<T> edge);

    boolean test(Vertex<T> a, Vertex<T> b);
}
