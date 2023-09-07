package ironsworn.structs;

import java.io.Serializable;

public class EnemyData implements Serializable {
    public String name;
    public LocationData location;
    public ItemData[] loot;

    public EnemyData(String name, LocationData location, ItemData[] loot) {
        this.name = name;
        this.location = location;:
        if(!location.contains(this)){
            location.add(this);
        }
        this.loot = loot;
    }
    public EnemyData(String name){
        this.name = name;
    }
}
