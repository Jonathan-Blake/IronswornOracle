package graph.search.impl;

import graph.search.Criteria;
import graph.search.SinglePredicateCriteria;
import graph.structs.NamedCampaignItem;
import ironsworn.structs.ItemData;

public class ItemCriteria {
    private ItemCriteria() {
    }

    public static Criteria<NamedCampaignItem> isItem() {
        return new SinglePredicateCriteria<>(vertex -> vertex.getContents() instanceof ItemData);
    }
}
