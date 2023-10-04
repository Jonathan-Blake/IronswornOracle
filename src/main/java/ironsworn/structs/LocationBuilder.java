package ironsworn.structs;

import graph.Relationship;
import graph.structs.NamedCampaignItem;
import ironsworn.StoryTeller;
import ironsworn.utility.Tuple;

import java.util.List;

public class LocationBuilder {
    public LocationData build(StoryTeller storyTeller) {
        return new LocationData(storyTeller.randomLocationName());
    }

    public List<Tuple<Relationship, NamedCampaignItem>> getRelationships() {
        return List.of();
    }
}
