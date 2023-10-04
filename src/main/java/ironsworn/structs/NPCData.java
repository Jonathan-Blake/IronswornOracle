package ironsworn.structs;

import graph.structs.NamedCampaignItem;

public class NPCData extends NamedCampaignItem {
    public LocationData location;
    public ItemData[] loot;
    public Opinion opinion;

    public NPCData(String name, LocationData location) {
        super(name);
        this.location = location;
    }
}
