package graph.constraints;

import graph.*;
import graph.structs.NamedCampaignItem;
import graph.structs.QuestNode;

import java.util.List;

public class IsAfter implements Constraint<NamedCampaignItem> {
    private static final Relationship RELATIONSHIP = new Relationship() {
        @Override
        public Relationship reverse() {
            return IsBefore.get();
        }

        @Override
        public String getRelationship() {
            return "Is After";
        }
    };
    public static final List<Relationship> BIDIRECTIONAL = List.of(get(), IsBefore.get());

    public static Relationship get() {
        return RELATIONSHIP;
    }

    @Override
    public boolean test(Edge<NamedCampaignItem> edge) {
        if (!BIDIRECTIONAL.contains(edge.relationship)) {
            System.out.println("Skipping edge");
            return true;
        }
        if (edge.a.getContents() instanceof QuestNode && edge.b.getContents() instanceof QuestNode) {
            return !Graph.detectRelationshipCycle(edge.a, IsBefore.get(), IsDuring.get()) && !Graph.detectRelationshipCycle(edge.b, IsAfter.get(), IsDuring.get());
        }
        return false;
    }

    @Override
    public boolean test(Vertex<NamedCampaignItem> a, Vertex<NamedCampaignItem> b) {
        return Graph.traversablePath(a, b, RELATIONSHIP, IsDuring.get());
    }
}
