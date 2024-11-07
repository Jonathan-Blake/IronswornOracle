package graph;

public class Edge<T> {
    public final Relationship relationship;
    public Vertex<T> a;
    public Vertex<T> b;

    public Edge(Vertex<T> a, Vertex<T> b, Relationship relationship) {
        this.a = a;
        this.b = b;
        this.relationship = relationship;
        a.edges.add(this);
        b.edges.add(this);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "relationship=" + relationship +
                "," + a +
                " => " + b +
                '}';
    }
}
