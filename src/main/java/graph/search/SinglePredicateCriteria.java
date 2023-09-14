package graph.search;

import graph.Vertex;

import java.util.function.Predicate;

public class SinglePredicateCriteria<T> implements Criteria<T> {
    private Predicate<Vertex<T>> predicate;

    public SinglePredicateCriteria(Predicate<Vertex<T>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean match(Vertex<T> object) {
        return this.predicate.test(object);
    }

    @Override
    public Criteria<T> orElse() {
        return null;
    }
}
