package graph;

import graph.search.Criteria;

import java.util.*;

public class Graph<T> {
    final Map<Vertex<T>, List<Edge<T>>> adjacency;
    private final List<Constraint<T>> rules;

    public Graph(Constraint<T>... rules) {
        this.rules = List.of(rules);
        this.adjacency = new HashMap<>();
    }

    public static <T> boolean detectRelationshipCycle(Vertex<T> start, Relationship... relationship) {
        return relationshipCycleHelper(start, List.of(relationship), new HashMap<>());
    }

    private static <T> boolean relationshipCycleHelper(Vertex<T> start,
                                                       List<Relationship> relationship,
                                                       Map<Vertex<T>, color> vertexColorMap
    ) {
        vertexColorMap.put(start, color.GRAY);
        for (Map.Entry<Vertex<T>, Relationship> entry : start.getAdjacent().entrySet()) {
            if (!relationship.contains(entry.getValue())) {
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

    public static <T> boolean traversablePath(Vertex<T> start, Vertex<T> end, Relationship... relationship) {
        return traversablePathhelper(start, end, List.of(relationship), new HashMap<>());
    }

    private static <T> boolean traversablePathhelper(Vertex<T> start,
                                                     Vertex<T> end,
                                                     List<Relationship> relationship,
                                                     Map<Vertex<T>, color> vertexColorMap
    ) {
        final Map<Vertex<T>, Relationship> entries = start.getAdjacent();
        if (entries.containsKey(end) && relationship.contains(entries.get(end))) {
            return true;
        }
        vertexColorMap.put(start, color.GRAY);
        for (Map.Entry<Vertex<T>, Relationship> entry : entries.entrySet()) {
            if (!relationship.contains(entry.getValue())) {
                continue;
            }
            if (vertexColorMap.computeIfAbsent(entry.getKey(), key -> color.WHITE) == color.WHITE &&
                    traversablePathhelper(entry.getKey(), end, relationship, vertexColorMap)) {
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

    public boolean removeVertex(T item) {
        final List<Edge<T>> edges = new ArrayList<>(adjacency.remove(getVertex(item)));
        boolean successfullyRemoved = edges.stream().allMatch(this::removeEdge);
        if (!successfullyRemoved) {
            throw new RuntimeException("Failed to remove edge vertex for some reason");
        }
        return true;
    }

    public Vertex<T> search(Criteria<T> searchCriteria) {
        final HashMap<Vertex<T>, color> vertexColorMap = new HashMap<>();
        Vertex<T> ret = null;
        for (Map.Entry<Vertex<T>, List<Edge<T>>> entry : adjacency.entrySet()) {
            ret = searchHelper(searchCriteria, entry.getKey(), vertexColorMap);
            if (ret != null) {
                break;
            }
        }
        if (ret == null && searchCriteria.orElse() != null) {
            ret = search(searchCriteria.orElse());
        }
        return ret;
    }

    private Vertex<T> searchHelper(Criteria<T> searchCriteria,
                                   Vertex<T> start,
                                   Map<Vertex<T>, color> vertexColorMap
    ) {
        if (searchCriteria.match(start)) {
            return start;
        }
        final Map<Vertex<T>, Relationship> entries = start.getAdjacent();
        vertexColorMap.put(start, color.GRAY);
        for (Map.Entry<Vertex<T>, Relationship> entry : entries.entrySet()) {
            if (vertexColorMap.computeIfAbsent(entry.getKey(), key -> color.WHITE) == color.WHITE) {
                Vertex<T> tmp = searchHelper(searchCriteria, entry.getKey(), vertexColorMap);
                if (tmp != null) {
                    return tmp;
                }
            }
        }
        vertexColorMap.put(start, color.BLACK);

        return null;
    }

    private Vertex<T> addVertexHelper(T item) {
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
        Vertex<T> aNode = addVertexHelper(a);
        Vertex<T> bNode = addVertexHelper(b);
        return addEdge(edgeType, aNode, bNode);
    }

    public boolean addEdge(Relationship edgeType, Vertex<T> a, Vertex<T> b) {
        assert adjacency.containsKey(a) && adjacency.containsKey(b);
        Edge<T> edge = a.link(b, edgeType);
        if (testRules(edge)) {
            adjacency.get(a).add(edge);
            adjacency.get(b).add(edge);
            return true;
        } else {
            System.out.println("Failed to set Edge " + edgeType);
            a.edges.remove(edge);
            b.edges.remove(edge);
            return false;
        }
    }

    private boolean removeEdge(Edge<T> tEdge) {
        return (tEdge.a.edges.remove(tEdge)
                && adjacency.get(tEdge.a).remove(tEdge)
                && tEdge.b.edges.remove(tEdge)
                && adjacency.get(tEdge.b).remove(tEdge));
    }

    private boolean testRules(Edge<T> edge) {
        boolean b = true;
        for (Constraint<T> tConstraint : this.rules) {
            if (!tConstraint.testEdgeConstraint(edge)) {
                System.out.println("Edge " + edge + " failed constraint " + tConstraint);
                b = false;
                break;
            }
        }
        if (b) {
            return true;
        } else {
            Vertex<T> tmp = edge.b;
            edge.b = edge.a;
            edge.a = tmp;
            return this.rules.stream().allMatch(rule -> rule.testEdgeConstraint(edge));
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
