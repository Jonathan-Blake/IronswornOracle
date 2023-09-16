package graph.constraints;

import graph.*;
import graph.structs.NamedCampaignItem;
import jdk.jshell.spi.ExecutionControl;

public class IsDuring implements Constraint<NamedCampaignItem> {
    private static final Relationship RELATIONSHIP = new Relationship() {
        @Override
        public Relationship reverse() {
            return this;
        }

        @Override
        public String getRelationship() {
            return "Is During";
        }
    };

    public static Relationship get() {
        return RELATIONSHIP;
    }

    @Override
    public boolean test(Edge<NamedCampaignItem> edge) {
        throw new RuntimeException(new ExecutionControl.NotImplementedException("What would this constraint even mean?"));
    }

    @Override
    public boolean test(Vertex<NamedCampaignItem> a, Vertex<NamedCampaignItem> b) {
        return Graph.traversablePath(a, b, RELATIONSHIP);
    }
}
