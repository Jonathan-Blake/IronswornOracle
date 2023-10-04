package ironsworn.structs;

import graph.Relationship;
import graph.structs.NamedCampaignItem;
import ironsworn.StoryTeller;
import ironsworn.utility.Tuple;

import java.util.List;

public class ItemBuilder {
    public ItemData build(StoryTeller storyTeller) {
        return new ItemData(storyTeller.randomItemName());
    }

    public List<Tuple<Relationship, NamedCampaignItem>> getRelationships() {
        return List.of();
    }
}
