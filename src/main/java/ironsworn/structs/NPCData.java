package ironsworn.structs;

import java.io.Serializable;

public class NPCData implements Serializable {
    public String name;
    public LocationData location;
    public ItemData[] loot;
    boolean friendly;

    public NPCData(String name, LocationData location) {
        this.name = name;
        this.location = location;
    }
}
