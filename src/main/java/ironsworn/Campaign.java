package ironsworn;

import ironsworn.structs.*;

import java.io.Serializable;

public class Campaign implements Serializable {
    public NPCData[] npcs;
    public LocationData[] locations;
    public EnemyData[] enemies;
    public ItemData[] items;

    public SeedData[] knowledgeSeeds;
    public SeedData[] comfortSeeds;
    public SeedData[] justiceSeeds;

    public static NPCData getFriendlyNPC(String target, LocationData location) {
        return new NPCData(target, location);
    }

    public static EnemyData getEnemy(String target, LocationData location) {
        return new EnemyData(target, location, new ItemData[]{});
    }


    public NPCData GetFriendlyNPC() {
        return null;
    }

    public LocationData GetLocation() {
        return null;
    }

    public EnemyData GetEnemy() {
        return null;
    }


    public ItemData GetItem() {
        return null;
    }
}
