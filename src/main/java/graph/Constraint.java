package graph;

public interface Constraint<T> {
    boolean testEdgeConstraint(Edge<T> edge);

    boolean testPossibleConnection(Vertex<T> a, Vertex<T> b);
}
