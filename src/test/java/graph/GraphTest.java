package graph;

import graph.constraints.AtLocation;
import graph.constraints.IsAfter;
import graph.constraints.IsBefore;
import graph.constraints.IsDuring;
import graph.search.Criteria;
import graph.structs.CampaignLocation;
import graph.structs.NPC;
import graph.structs.NamedCampaignItem;
import ironsworn.Campaign;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.actions.Subquest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GraphTest {

    @Mock
    private Relationship MockRelationship;

    private static class OnlyOneEdge implements Constraint<Integer> {
        @Override
        public boolean testEdgeConstraint(Edge<Integer> edge) {
            if (edge.a.getEdges().size() > 1) {
                return false;
            } else {
                return edge.b.getEdges().size() > 1;
            }
        }

        @Override
        public boolean testPossibleConnection(Vertex<Integer> rVertex, Vertex<Integer> mVertex) {
            return rVertex.edges.size() <= 1 && mVertex.edges.size() <= 1;
        }
    }

    private static class GreaterThan implements Constraint<Integer> {

        public static Relationship get() {
            return GT;
        }

        @Override
        public boolean testEdgeConstraint(Edge<Integer> edge) {
            return edge.a.getContents() >= edge.b.getContents();
        }

        @Override
        public boolean testPossibleConnection(Vertex<Integer> a, Vertex<Integer> b) {
            return Graph.traversablePath(a, b, get());
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

            @Override
            public String toString() {
                return "Less Than";
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

            @Override
            public String toString() {
                return "Greater Than";
            }
        };


    }

    private static class IntegerCriteria {
        public static Criteria<Integer> is(int i) {
            return new Criteria<>() {
                @Override
                public boolean match(Vertex<Integer> object) {
                    return object.getContents() == i;
                }

                @Override
                public Criteria<Integer> orElse() {
                    return null;
                }
            };
        }

        public static Criteria<Integer> greatest() {
            return new Criteria<>() {
                @Override
                public boolean match(Vertex<Integer> object) {
                    return !object.getAdjacent().containsValue(GreaterThan.get());
                }

                @Override
                public Criteria<Integer> orElse() {
                    return null;
                }
            };
        }
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

        @Test
        void test_Search_FindsRightNumber_IsolatedGraph() {
            Graph<Integer> g = new Graph<>();
            assertTrue(g.addVertex(1));
            assertTrue(g.addVertex(2));
            assertTrue(g.addVertex(3));

            assertEquals(g.getVertex(3), g.search(Criteria.allOf(IntegerCriteria.is(3)).build()));
            assertEquals(g.getVertex(2), g.search(Criteria.allOf(IntegerCriteria.is(2)).build()));
            assertEquals(g.getVertex(1), g.search(Criteria.allOf(IntegerCriteria.is(1)).build()));
            assertNull(g.search(Criteria.allOf(IntegerCriteria.is(0)).build()));
            assertNull(g.search(Criteria.allOf(IntegerCriteria.is(4)).build()));
        }

        @Test
        void test_Search_FindsRightNumber_OnSecondaryCriteria() {
            Graph<Integer> g = new Graph<>();
            g.addEdge(GreaterThan.get(), 1, 2);
            g.addEdge(GreaterThan.get(), 2, 3);
            System.out.println(g.getVertex(1).getAdjacent());
            System.out.println(g.getVertex(2).getAdjacent());
            System.out.println(g.getVertex(3).getAdjacent());

            assertNull(g.search(
                    Criteria.allOf(IntegerCriteria.is(4)).build()));
            assertEquals(g.getVertex(3), g.search(
                    Criteria.allOf(IntegerCriteria.is(4))
                            .orElse(IntegerCriteria.greatest()).build()));
        }

        @Test
        void test_Search_ReturnsNullIfNoSuchNumber_IsolatedGraph() {
            Graph<Integer> g = new Graph<>();
            assertTrue(g.addVertex(1));
            assertTrue(g.addVertex(2));
            assertTrue(g.addVertex(3));

            assertNull(g.search(Criteria.allOf(IntegerCriteria.is(0)).build()));
            assertNull(g.search(Criteria.allOf(IntegerCriteria.is(4)).build()));
        }
    }

    @Nested
    class TemporalGraph {
        @Test
        void assignSetsRelativePositions() {
            final Campaign campaign = new Campaign();
            StoryTeller storyTeller = new StoryTeller(campaign);
            Subquest root = storyTeller.assignActions(List.of("learn", "kill", "goto"));
            final Vertex<NamedCampaignItem> learn = campaign.getVertex(root.getSubActions().get(0));
            final Vertex<NamedCampaignItem> kill = campaign.getVertex(root.getSubActions().get(1));
            final Vertex<NamedCampaignItem> GoTo = campaign.getVertex(root.getSubActions().get(2));
            assertTrue(new IsBefore().testPossibleConnection(GoTo, learn));
            assertTrue(new IsBefore().testPossibleConnection(GoTo, kill));
            assertFalse(new IsBefore().testPossibleConnection(learn, GoTo));
            assertFalse(new IsBefore().testPossibleConnection(kill, GoTo));
            assertTrue(new IsAfter().testPossibleConnection(learn, GoTo));
            assertTrue(new IsAfter().testPossibleConnection(kill, GoTo));
            assertFalse(new IsAfter().testPossibleConnection(GoTo, learn));
            assertFalse(new IsAfter().testPossibleConnection(GoTo, kill));
            System.out.println(QuestAction.printIndentedTree(root, 0));
        }

        @Test
        void expandSetsRelativePositions() {
            final Campaign campaign = new Campaign();
            StoryTeller storyTeller = new StoryTeller(campaign);
            Subquest root = storyTeller.assignActions(List.of("learn", "kill", "goto"));
            final Vertex<NamedCampaignItem> learn = campaign.getVertex(root.getSubActions().get(0));
            final Vertex<NamedCampaignItem> kill = campaign.getVertex(root.getSubActions().get(1));
            final Vertex<NamedCampaignItem> goTo = campaign.getVertex(root.getSubActions().get(2));
            ((QuestAction) kill.getContents()).expand(storyTeller);
            Vertex<NamedCampaignItem> goToTarget = campaign.getVertex(((QuestAction) kill.getContents()).getSubActions().get(0));
            Vertex<NamedCampaignItem> killTarget = campaign.getVertex(((QuestAction) kill.getContents()).getSubActions().get(1));
            assertTrue(new IsDuring().testPossibleConnection(goToTarget, kill));
//            assertTrue(new IsDuring().test(kill, killTarget));
            assertTrue(new IsDuring().testPossibleConnection(killTarget, kill));
            assertFalse(new IsDuring().testPossibleConnection(learn, kill));
            assertFalse(new IsDuring().testPossibleConnection(kill, goTo));

            assertTrue(new IsBefore().testPossibleConnection(goToTarget, learn));
            assertTrue(new IsBefore().testPossibleConnection(killTarget, learn));
            assertFalse(new IsBefore().testPossibleConnection(goToTarget, goTo));
            assertFalse(new IsBefore().testPossibleConnection(killTarget, goTo));
            assertTrue(new IsAfter().testPossibleConnection(goToTarget, goTo));
            assertTrue(new IsAfter().testPossibleConnection(killTarget, goTo));
            assertFalse(new IsAfter().testPossibleConnection(goTo, goToTarget));
            assertFalse(new IsAfter().testPossibleConnection(goTo, killTarget));

            assertFalse(new IsDuring().testPossibleConnection(goToTarget, killTarget));
            assertFalse(new IsDuring().testPossibleConnection(killTarget, goToTarget));

            System.out.println(QuestAction.printIndentedTree(root, 0));
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
            assertTrue(Graph.traversablePath(oneVertex, fiftyNineVertex, GreaterThan.get()));
            assertFalse(Graph.traversablePath(fiftyNineVertex, oneVertex, GreaterThan.get()));
            assertFalse(Graph.detectRelationshipCycle(oneVertex, GreaterThan.get()));
        }
    }
}