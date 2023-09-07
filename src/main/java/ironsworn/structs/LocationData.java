package ironsworn.structs;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class LocationData implements Serializable {
    public String name;
    public List<EnemyData> enemies;
    public NPCData[] npcs;

    public LocationData(String name) {
        this.name = name;
    }

    public boolean contains(EnemyData enemyData) {
        if(enemies == null){
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
