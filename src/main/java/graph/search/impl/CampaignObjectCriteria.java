package graph.search.impl;

import graph.search.Criteria;
import graph.search.SinglePredicateCriteria;
import graph.structs.NamedCampaignItem;

public class CampaignObjectCriteria {
    private CampaignObjectCriteria() {
    }

    public static Criteria<NamedCampaignItem> hasName(String target) {
        return new SinglePredicateCriteria<>(node -> node.getContents().getName().equals(target));
    }
}
