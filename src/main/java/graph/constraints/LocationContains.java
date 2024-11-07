package graph.constraints;

import graph.*;
import graph.structs.CampaignLocation;
import graph.structs.NamedCampaignItem;

public class LocationContains implements Constraint<NamedCampaignItem> {
    static final Relationship RELATIONSHIP = new Relationship() {
        @Override
        public Relationship reverse() {
            return AtLocation.RELATIONSHIP;
        }

        @Override
        public String getRelationship() {
            return "Location Contains";
        }
    };

    public static Relationship get() {
        return RELATIONSHIP;
    }

    @Override
    public boolean testEdgeConstraint(Edge<NamedCampaignItem> edge) {
        if (edge.relationship != RELATIONSHIP) {
            return true;
        }
        if (!(edge.a.getContents() instanceof CampaignLocation)) {
            return false;
        }
        return edge.b.getEdges().stream().anyMatch(e -> e.relationship == AtLocation.get());
    }

    @Override
    public boolean testPossibleConnection(Vertex<NamedCampaignItem> rVertex, Vertex<NamedCampaignItem> mVertex) {
        return Graph.traversablePath(rVertex, mVertex, get());
    }
}
