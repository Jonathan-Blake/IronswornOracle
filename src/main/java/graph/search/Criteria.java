package graph.search;

import graph.Vertex;

import java.util.Arrays;

public interface Criteria<T> {
    static <T> CriteriaBuilder<T> allOf(Criteria<T>... requirements) {
        CriteriaBuilder<T> c = new CriteriaBuilder<>();
        c.orElse(new Criteria<>() {
            @Override
            public boolean match(Vertex<T> object) {
                return Arrays.stream(requirements).allMatch(test -> test.match(object));
            }

            @Override
            public Criteria<T> orElse() {
                return null;
            }
        });
        return c;
    }

    static <T> CriteriaBuilder<T> noneMatch(Criteria<T>... requirements) {
        CriteriaBuilder<T> c = new CriteriaBuilder<>();
        c.orElse(new Criteria<>() {
            @Override
            public boolean match(Vertex<T> object) {
                return Arrays.stream(requirements).noneMatch(test -> test.match(object));
            }

            @Override
            public Criteria<T> orElse() {
                return null;
            }
        });
        return c;
    }

    static <T> CriteriaBuilder<T> anyOf(Criteria<T>... requirements) {
        CriteriaBuilder<T> c = new CriteriaBuilder<>();
        c.orElse(new Criteria<>() {
            @Override
            public boolean match(Vertex<T> object) {
                return Arrays.stream(requirements).anyMatch(test -> test.match(object));
            }

            @Override
            public Criteria<T> orElse() {
                return null;
            }
        });
        return c;
    }

    boolean match(Vertex<T> object);

    Criteria<T> orElse();
}
