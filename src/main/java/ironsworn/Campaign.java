package ironsworn;

import graph.Graph;
import graph.Vertex;
import graph.constraints.AtLocation;
import graph.constraints.IsAfter;
import graph.search.Criteria;
import graph.search.impl.LocationCriteria;
import graph.structs.NamedCampaignItem;
import ironsworn.structs.EnemyData;
import ironsworn.structs.ItemData;
import ironsworn.structs.LocationData;
import ironsworn.structs.NPCData;

public class Campaign extends Graph<NamedCampaignItem> {
//    public NPCData[] npcs;
//    public LocationData[] locations;
//    public EnemyData[] enemies;
//    public ItemData[] items;
//
//    public SeedData[] knowledgeSeeds;
//    public SeedData[] comfortSeeds;
//    public SeedData[] justiceSeeds;

    public Campaign() {
        super(new AtLocation(), new IsAfter());// No Cycles in locations or Stories
    }

    public static NPCData getFriendlyNPC(String target, LocationData location) {
        return new NPCData(target, location);
    }

    public NPCData getFriendlyNPC() {
        return new NPCData("Frank", null);
    }

    public static EnemyData getEnemy(String target, LocationData location) {
        return new EnemyData(target, location, new ItemData[]{});
    }

    public EnemyData getEnemy() {
        return null;
    }

    public LocationData getLocation() {
        final Vertex<NamedCampaignItem> search = search(Criteria.allOf(LocationCriteria.isLocation()).build());
        if (search != null && search.getContents() instanceof LocationData l) {
            return l;
        }
        return null;
    }


    public ItemData getItem() {
        return null;
    }
}
