package graph;

import graph.constraints.AtLocation;
import graph.structs.CampaignLocation;
import graph.structs.NPC;
import graph.structs.NamedCampaignItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GraphTest {

    @Mock
    private Relationship MockRelationship;

    private static class OnlyOneEdge implements Constraint<Integer> {
        @Override
        public boolean test(Edge<Integer> edge) {
            if (edge.a.getEdges().size() > 1) {
                return false;
            } else {
                return edge.b.getEdges().size() > 1;
            }
        }

        @Override
        public boolean test(Vertex<Integer> rVertex, Vertex<Integer> mVertex) {
            return rVertex.edges.size() <= 1 && mVertex.edges.size() <= 1;
        }
    }

    private static class GreaterThan implements Constraint<Integer> {

        public static Relationship get() {
            return GT;
        }

        @Override
        public boolean test(Edge<Integer> edge) {
            return edge.a.getContents() >= edge.b.getContents();
        }

        @Override
        public boolean test(Vertex<Integer> a, Vertex<Integer> b) {
            return Graph.search(a, b, get());
        }

        private static final Relationship LT = new Relationship() {
            @Override
            public Relationship reverse() {
                return GT;
            }

            @Override
            public String getRelationship() {
                return null;
            }
        };


        public static final Relationship GT = new Relationship() {
            @Override
            public Relationship reverse() {
                return LT;
            }

            @Override
            public String getRelationship() {
                return null;
            }
        };


    }

    @Nested
    class NonStatic {
        @Test
        void addVertex_DoesNotError() {
            Graph<Integer> g = new Graph<>();
            assertTrue(g.addVertex(1));
            assertTrue(g.addVertex(2));
            assertTrue(g.addVertex(3));
        }

        @Test
        void addVertex_FailsOnDuplicates() {
            Graph<Integer> g = new Graph<>();
            assertTrue(g.addVertex(1));
            assertFalse(g.addVertex(1));
            assertFalse(g.addVertex(1));
        }

        @Test
        void addEdge_DoesNotError() {
            Graph<Integer> g = new Graph<>();
            g.addVertex(1);
            g.addVertex(2);

            assertTrue(g.addEdge(MockRelationship, 1, 2));
        }

        @Test
        void addEdge_DoesError() {
            Graph<Integer> g = new Graph<>(new OnlyOneEdge());
            g.addVertex(1);
            g.addVertex(2);
            g.addVertex(3);

            g.addEdge(MockRelationship, 1, 2);
            assertFalse(g.addEdge(MockRelationship, 2, 3));
        }

        @Test
        void addEdge_CreatesNewVertexes_IfNeeded() {
            Graph<Integer> g = new Graph<>();
            g.addVertex(1);
            assertNull(g.getVertex(2));
            g.addEdge(MockRelationship, 1, 2);
            Vertex<Integer> two = g.getVertex(2);
            assertNotNull(two);
            assertEquals(2, two.getContents());
        }

        @Test
        void getVertex_returnsCorrectVertex() {
            Graph<Integer> g = new Graph<>();
            assertTrue(g.addVertex(1));
            assertTrue(g.addVertex(2));
            assertTrue(g.addVertex(3));
            g.addEdge(MockRelationship, 1, 2);

            final Vertex<Integer> three = g.getVertex(3);

            assertEquals(3, (int) three.contents);
            assertTrue(three.edges.isEmpty());

            final Vertex<Integer> two = g.getVertex(2);
            assertEquals(2, (int) two.contents);
            assertFalse(two.edges.isEmpty());

            assertEquals(two.getEdges().get(0).a, g.getVertex(1));
        }

        @Test
        void getVertex_returnsNullIfNoSuchVertex() {
            Graph<Integer> g = new Graph<>();
            assertTrue(g.addVertex(1));
            assertTrue(g.addVertex(2));
            assertTrue(g.addVertex(3));

            final Vertex<Integer> three = g.getVertex(4);

            assertNull(three);
        }

        @Test
        void test_AllowsNPCSAtLocation() {
            Graph<NamedCampaignItem> g = new Graph<>(new AtLocation());
            final NPC frank = new NPC("Frank");
            g.addVertex(frank);
            final CampaignLocation sunny_town = new CampaignLocation("Sunny Town");
            g.addVertex(sunny_town);
            assertTrue(g.addEdge(AtLocation.get(), frank, sunny_town));
        }

        @Test
        void test_AllowsIfReverseIsValid() {
            Graph<NamedCampaignItem> g = new Graph<>(new AtLocation());
            final NPC frank = new NPC("Frank");
            g.addVertex(frank);
            final CampaignLocation sunny_town = new CampaignLocation("Sunny Town");
            g.addVertex(sunny_town);
            assertTrue(g.addEdge(AtLocation.get(), sunny_town, frank));
        }
    }

    @Nested
    class Static {
        private static Graph<Integer> g;

        @BeforeAll
        static void setup() {
            g = new Graph<>();
            assertTrue(g.addVertex(0));
            assertTrue(g.addVertex(70));
            for (int i = 0; i < 60; i++) {
                assertTrue(g.addVertex(i + 1));
                g.addEdge(GreaterThan.get(), i, i + 1);
                g.addEdge(GreaterThan.get(), i, 70);
            }
        }

        @Test
        void test() {
            final Vertex<Integer> oneVertex = g.getVertex(1);
            final Vertex<Integer> fiftyNineVertex = g.getVertex(59);
            assertNotNull(oneVertex);
            assertNotNull(fiftyNineVertex);
            assertTrue(Graph.search(oneVertex, fiftyNineVertex, GreaterThan.get()));
            assertFalse(Graph.search(fiftyNineVertex, oneVertex, GreaterThan.get()));
            assertFalse(Graph.detectRelationshipCycle(oneVertex, GreaterThan.get()));
        }
    }
}