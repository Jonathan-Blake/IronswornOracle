package graph.constraints;

import graph.*;
import graph.structs.NamedCampaignItem;
import graph.structs.QuestNode;

import java.util.List;

public class IsBefore implements Constraint<NamedCampaignItem> {
    public static final Relationship RELATIONSHIP = new Relationship() {
        @Override
        public Relationship reverse() {
            return IsAfter.get();
        }

        @Override
        public String getRelationship() {
            return "Is Before";
        }
    };
    public static final List<Relationship> BIDIRECTIONAL = List.of(get(), IsAfter.get());

    public static Relationship get() {
        return RELATIONSHIP;
    }

    @Override
    public boolean testEdgeConstraint(Edge<NamedCampaignItem> edge) {
        if (!BIDIRECTIONAL.contains(edge.relationship)) {
//            System.out.println("Skipping edge");
            return true;
        }
        if (edge.a.getContents() instanceof QuestNode && edge.b.getContents() instanceof QuestNode) {
            return !Graph.detectRelationshipCycle(edge.a, IsAfter.get(), IsDuring.get()) && !Graph.detectRelationshipCycle(edge.b, IsBefore.get(), IsDuring.get());
        }
        return false;
    }

    @Override
    public boolean testPossibleConnection(Vertex<NamedCampaignItem> a, Vertex<NamedCampaignItem> b) {
        return Graph.traversablePath(a, b, RELATIONSHIP, IsDuring.get());
    }
}
