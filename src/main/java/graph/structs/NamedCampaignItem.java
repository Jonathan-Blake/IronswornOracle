package graph.structs;

public class NamedCampaignItem {
    String name;

    public NamedCampaignItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass() + "{"
                + getName() +
                " }";
    }
}
