package ironsworn.structs;

import graph.Relationship;
import graph.structs.NamedCampaignItem;
import ironsworn.StoryTeller;
import ironsworn.utility.Tuple;

import java.util.List;

public class NPCBuilder {
    private final LocationBuilder locationRequirements = new LocationBuilder();
    private Opinion opinion;
    private String name;
    private LocationData location;

    public NPCBuilder relationship(Opinion friendly) {
        this.opinion = friendly;
        return this;
    }

    public NPCData build(StoryTeller storyTeller) {
        final NPCData npcData = new NPCData(
                this.name != null ? this.name : storyTeller.randomName(),
                this.location != null ? this.location : storyTeller.createLocation(this.locationRequirements)
        );
        npcData.opinion = opinion;
        return npcData;
    }

    public List<Tuple<Relationship, NamedCampaignItem>> getRelationships() {
        return List.of();
    }

    public NPCBuilder name(String name) {
        this.name = name;
        return this;
    }
}
