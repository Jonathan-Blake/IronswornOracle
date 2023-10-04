package graph.search.impl;

import graph.search.Criteria;
import graph.search.SinglePredicateCriteria;
import graph.structs.NamedCampaignItem;
import ironsworn.structs.NPCData;
import ironsworn.structs.Opinion;

public class NPCCriteria {
    private NPCCriteria() {
    }

    public static Criteria<NamedCampaignItem> isNPC() {
        return new SinglePredicateCriteria<>(vertex -> vertex.getContents() instanceof NPCData);
    }

    public static Criteria<NamedCampaignItem> isFriendly() {
        return new SinglePredicateCriteria<>(vertex -> (vertex.getContents() instanceof NPCData friend) && friend.opinion == Opinion.FRIENDLY);
    }

    public static Criteria<NamedCampaignItem> isEnemy() {
        return new SinglePredicateCriteria<>(vertex -> (vertex.getContents() instanceof NPCData friend) && friend.opinion == Opinion.ENEMY);
    }
}
