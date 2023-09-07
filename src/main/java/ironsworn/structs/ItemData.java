package ironsworn.structs;

import java.io.Serializable;

public class ItemData implements Serializable {
    public String name;

    public ItemData(String name) {
        this.name = name;
    }
}
