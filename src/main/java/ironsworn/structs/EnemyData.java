package ironsworn.structs;

import java.io.Serializable;

public class EnemyData extends NPCData implements Serializable {
    public ItemData[] loot;

    public EnemyData(String name, LocationData location, ItemData[] loot) {
        super(name, location);
        if (!location.contains(this)) {
            location.add(this);
        }
        this.loot = loot;
    }

    public EnemyData(String name) {
        super(name, null);
    }
}
