package ironsworn;

import graph.Graph;
import graph.Relationship;
import graph.Vertex;
import graph.constraints.AtLocation;
import graph.constraints.IsAfter;
import graph.search.Criteria;
import graph.search.impl.ItemCriteria;
import graph.search.impl.LocationCriteria;
import graph.search.impl.NPCCriteria;
import graph.structs.NamedCampaignItem;
import ironsworn.oracle.Oracle;
import ironsworn.structs.ItemData;
import ironsworn.structs.LocationData;
import ironsworn.structs.NPCData;
import ironsworn.utility.Tuple;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Campaign extends Graph<NamedCampaignItem> {

    public Oracle oracle;

    public Campaign() {
        super(new AtLocation(), new IsAfter());// No Cycles in locations or Stories
        try {
            oracle = new Oracle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NamedCampaignItem create(Supplier<NamedCampaignItem> initializer, List<Tuple<Relationship, NamedCampaignItem>> relationships) {
        final NamedCampaignItem nodeData = initializer.get();
        addVertex(nodeData);
        Vertex<NamedCampaignItem> v = getVertex(nodeData);
        try {
            relationships.forEach(data -> {
                if (!addEdge(data.a(), v, getVertex(data.b()))) {
                    throw new RuntimeException("Failed to initalise");
                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
            removeVertex(nodeData);
            throw e;
        }
        return nodeData;
    }

    public Optional<NPCData> getFriendlyNPC(Criteria<NamedCampaignItem> criteria) {
        return Optional.ofNullable(search(
                Criteria.allOf(
                        NPCCriteria.isNPC(),
                        NPCCriteria.isFriendly(),
                        criteria
                ).build()
        )).map(node -> (NPCData) node.getContents());
    }

    public Optional<NPCData> getEnemy(Criteria<NamedCampaignItem> requirements) {
        return Optional.ofNullable(search(
                Criteria.allOf(
                        NPCCriteria.isNPC(),
                        NPCCriteria.isEnemy(),
                        requirements
                ).build()
        )).map(node -> (NPCData) node.getContents());
    }

    public Optional<LocationData> getLocation(Criteria<NamedCampaignItem> requirements) {
        return Optional.ofNullable(search(
                Criteria.allOf(
                        LocationCriteria.isLocation(),
                        requirements
                ).build()
        )).map(node -> (LocationData) node.getContents());
    }

    public Optional<ItemData> getItem(Criteria<NamedCampaignItem> requirements) {
        return Optional.ofNullable(search(
                Criteria.allOf(
                        ItemCriteria.isItem(),
                        requirements
                ).build()
        )).map(node -> (ItemData) node.getContents());
    }
}
