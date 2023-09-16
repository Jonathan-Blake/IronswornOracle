package ironsworn.structs;

import graph.structs.NamedCampaignItem;

import java.util.LinkedList;
import java.util.List;

public class LocationData extends NamedCampaignItem {
    public List<EnemyData> enemies;
    public NPCData[] npcs;

    public LocationData(String name) {
        super(name);
    }

    public boolean contains(EnemyData enemyData) {
        if (enemies == null) {
            return false;
        } else {
            return enemies.contains(enemyData);
        }
    }

    public boolean add(EnemyData enemyData) {
        if(enemies == null){
            enemies = new LinkedList<>();
        }
        return enemies.add(enemyData);
    }
}
