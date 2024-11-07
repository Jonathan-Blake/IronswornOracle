package graph.constraints;

import graph.*;
import graph.structs.NamedCampaignItem;
import jdk.jshell.spi.ExecutionControl;

public class ExpansionOf implements Constraint<NamedCampaignItem> {
    private static final Relationship RELATIONSHIP = new Relationship() {
        @Override
        public Relationship reverse() {
            return IsDuring.get();
        }

        @Override
        public String getRelationship() {
            return "Expansion Of";
        }
    };

    public static Relationship get() {
        return RELATIONSHIP;
    }

    @Override
    public boolean testEdgeConstraint(Edge<NamedCampaignItem> edge) {
        throw new RuntimeException(new ExecutionControl.NotImplementedException("What would this constraint even mean?"));
    }

    @Override
    public boolean testPossibleConnection(Vertex<NamedCampaignItem> a, Vertex<NamedCampaignItem> b) {
        return Graph.traversablePath(a, b, RELATIONSHIP);
    }
}
