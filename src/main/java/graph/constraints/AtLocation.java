package graph.constraints;

import graph.*;
import graph.structs.CampaignLocation;
import graph.structs.NamedCampaignItem;

import java.util.List;

public class AtLocation implements Constraint<NamedCampaignItem> {
    static final Relationship RELATIONSHIP = new Relationship() {
        @Override
        public Relationship reverse() {
            return LocationContains.get();
        }

        @Override
        public String getRelationship() {
            return "At location";
        }
    };

    public static Relationship get() {
        return RELATIONSHIP;
    }

    @Override
    public boolean test(Edge<NamedCampaignItem> edge) {
        if (edge.relationship != AtLocation.RELATIONSHIP) {
            System.out.println("Skipping edge");
            return true;
        }
        if (!(edge.b.getContents() instanceof CampaignLocation)) {
            System.out.println("Relationship at must be location.");
            return false;
        }
        // A can only be 'at' one location
        for (Edge<? extends NamedCampaignItem> e : edge.a.getEdges().stream()
                .filter(e -> e != edge)
                .filter(e -> e.relationship == AtLocation.RELATIONSHIP)
                .toList()) {
            if (e.a.getContents() instanceof CampaignLocation) {
                if (Graph.detectRelationshipCycle(e.b, AtLocation.RELATIONSHIP)) {
                    System.out.println("No At Location Cycles allowed");
                    return false;
                }
                System.out.println("Location is not in loop");
            } else {
                System.out.println("Edge is non location location");
                if (e.a.getEdges().stream()
                        .filter(edge1 -> (List.of(AtLocation.RELATIONSHIP, AtLocation.RELATIONSHIP.reverse()).contains(edge1.relationship)))
                        .count() != 1) {
                    System.out.println("Item can only be in one place.");
                    return false;
                }
            }
        }
        return edge.b.getContents() instanceof CampaignLocation;
    }

    @Override
    public boolean test(Vertex<NamedCampaignItem> at, Vertex<NamedCampaignItem> location) {
        return Graph.traversablePath(at, location, get());
    }


}
