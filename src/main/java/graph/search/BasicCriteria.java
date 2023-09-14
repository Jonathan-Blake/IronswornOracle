package graph.search;

import graph.Vertex;

public class BasicCriteria<T> implements Criteria<T> {
    private final Criteria<T> criteria;
    private final Criteria<T> next;

    public BasicCriteria(Criteria<T> criteria, Criteria<T> next) {
        this.criteria = criteria;
        this.next = next;
    }

    @Override
    public boolean match(Vertex<T> object) {
        return this.criteria.match(object);
    }

    @Override
    public Criteria<T> orElse() {
        return this.next;
    }
}
