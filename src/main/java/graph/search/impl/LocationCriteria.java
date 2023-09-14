package graph.search.impl;

import graph.constraints.AtLocation;
import graph.constraints.LocationContains;
import graph.search.Criteria;
import graph.search.SinglePredicateCriteria;
import graph.structs.NamedCampaignItem;

public class LocationCriteria {
    private LocationCriteria() {
        //Covering static class.
    }

    public static Criteria<NamedCampaignItem> locationOf(NamedCampaignItem find) {
        return new SinglePredicateCriteria<>(vertex -> vertex.getAdjacent()
                .entrySet()
                .stream()
                .anyMatch(entry -> entry.getValue() == LocationContains.get() && entry.getKey().getContents() == find));
    }

    public static Criteria<NamedCampaignItem> contentsOfLocation(NamedCampaignItem find) {
        return new SinglePredicateCriteria<>(vertex -> vertex.getAdjacent()
                .entrySet()
                .stream()
                .anyMatch(entry -> entry.getValue() == AtLocation.get() && entry.getKey().getContents() == find));
    }

    public static Criteria<NamedCampaignItem> noLocation() {
        return new SinglePredicateCriteria<>(vertex -> vertex.getAdjacent()
                .entrySet()
                .stream()
                .noneMatch(entry -> (
                        entry.getValue() == LocationContains.get() ||
                                entry.getValue() == AtLocation.get()
                )));
    }
}
