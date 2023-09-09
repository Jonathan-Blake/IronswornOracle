package graph;

import java.util.*;

public class Graph<T> {
    final Map<Vertex<T>, List<Edge<T>>> adjacency;
    private final List<Constraint<T>> rules;

    public Graph(Constraint<T>... rules) {
        this.rules = List.of(rules);
        this.adjacency = new HashMap<>();
    }

    public static <T> boolean detectRelationshipCycle(Vertex<T> start, Relationship relationship) {
        return relationshipCycleHelper(start, relationship, new HashMap<>());
    }

    private static <T> boolean relationshipCycleHelper(Vertex<T> start,
                                                       Relationship relationship,
                                                       Map<Vertex<T>, color> vertexColorMap
    ) {
        vertexColorMap.put(start, color.GRAY);
        for (Map.Entry<Vertex<T>, Relationship> entry : start.getAdjacent().entrySet()) {
            if (relationship != entry.getValue()) {
                continue;
            }
            if (vertexColorMap.get(entry.getKey()) == color.GRAY) {
                return true;
            } else {
                if (vertexColorMap.computeIfAbsent(entry.getKey(), key -> color.WHITE) == color.WHITE &&
                        relationshipCycleHelper(entry.getKey(), relationship, vertexColorMap)) {
                    return true;
                }
            }
        }
        vertexColorMap.put(start, color.BLACK);

        return false;
    }

    public static <T> boolean search(Vertex<T> start, Vertex<T> end, Relationship relationship) {
        return searchHelper(start, end, relationship, new HashMap<>());
    }

    private static <T> boolean searchHelper(Vertex<T> start,
                                            Vertex<T> end,
                                            Relationship relationship,
                                            Map<Vertex<T>, color> vertexColorMap
    ) {
        final Map<Vertex<T>, Relationship> entries = start.getAdjacent();
        if (entries.containsKey(end) && entries.get(end) == relationship) {
            return true;
        }
        vertexColorMap.put(start, color.GRAY);
        for (Map.Entry<Vertex<T>, Relationship> entry : entries.entrySet()) {
            if (relationship != entry.getValue()) {
                continue;
            }
            if (vertexColorMap.computeIfAbsent(entry.getKey(), key -> color.WHITE) == color.WHITE &&
                    searchHelper(entry.getKey(), end, relationship, vertexColorMap)) {
                return true;
            }
        }
        vertexColorMap.put(start, color.BLACK);

        return false;
    }

    public boolean addVertex(T item) {
        final Vertex<T> key = new Vertex<>(item);
        if (!adjacency.containsKey(key)) {
            adjacency.put(key, new ArrayList<>());
            return true;
        } else {
            return false;
        }
    }

    private Vertex<T> _addVertex(T item) {
        final Vertex<T> key = new Vertex<>(item);
        if (!adjacency.containsKey(key)) {
            adjacency.put(key, new ArrayList<>());
            return key;
        } else {
            return adjacency.keySet()
                    .stream()
                    .filter(vertex -> Objects.equals(vertex, key))
                    .findFirst()
                    .orElseThrow();
        }
    }

    public boolean addEdge(Relationship edgeType, T a, T b) {
        Vertex<T> aNode = _addVertex(a);
        Vertex<T> bNode = _addVertex(b);
        Edge<T> edge = aNode.link(bNode, edgeType);
        if (testRules(edge)) {
            return true;
        } else {
            aNode.edges.remove(edge);
            bNode.edges.remove(edge);
            return false;
        }
    }

    private boolean testRules(Edge<T> edge) {
        if (this.rules.stream().allMatch(rule -> rule.test(edge))) {
            return true;
        } else {
            Vertex<T> tmp = edge.b;
            edge.b = edge.a;
            edge.a = tmp;
            return this.rules.stream().allMatch(rule -> rule.test(edge));
        }
    }

    public Vertex<T> getVertex(T value) {
        final Vertex<T> tmp = new Vertex<>(value);
        if (adjacency.containsKey(tmp)) {
            for (Vertex<T> vertex : adjacency.keySet()) {
                if (vertex.getContents() == value) {
                    return vertex;
                }
            }
        }
        return null;
    }

    private enum color {
        WHITE, GRAY, BLACK
    }
}