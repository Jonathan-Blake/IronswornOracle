package ironsworn.actions.impl;

import graph.Relationship;
import graph.Vertex;
import graph.constraints.IsDuring;
import graph.search.Criteria;
import graph.structs.NamedCampaignItem;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCBuilder;
import ironsworn.structs.NPCData;
import ironsworn.structs.Opinion;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Kill extends BaseQuestAction {
    private NPCData enemyAttacking;

    public Kill() {
        super("kill");
    }

    @Override
    public void updateObjectives(Objective objectives) {
//        objectives.setEnemyAttacking(enemyAttacking);
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert (objectives.getEnemyAttacking() == null);
//                || storyTeller.campaign
//                .getVertex(this)
//                .getAdjacent()
//                .entrySet()
//                .stream()
//                .filter(kv -> kv.getValue().equals(IsDuring.get()))
//                .anyMatch(kv -> kv.getKey().getContents() instanceof Kill));


        final Vertex<NamedCampaignItem> vertex = objectives.campaign().getVertex(this);
        if (vertex != null && vertex.getAdjacent().containsValue(IsDuring.get())) {
            Optional<Map.Entry<Vertex<NamedCampaignItem>, Relationship>> parent = vertex.getAdjacent().entrySet().stream()
                    .filter(kv -> kv.getValue() == IsDuring.get())
                    .filter(kv -> kv.getKey().getContents() instanceof Kill)
                    .findAny();
            assert parent.isPresent();
            if (parent.get().getKey().getContents() instanceof Kill parentKill) {
                enemyAttacking = parentKill.enemyAttacking;
            } else {
                assert false;
            }
            objectives.add(enemyAttacking);
        } else {
            enemyAttacking = storyTeller.getEnemy(Criteria.noRequirements()).orElseGet(() -> storyTeller.createNPC(new NPCBuilder().relationship(Opinion.ENEMY)));
            objectives.setEnemyAttacking(enemyAttacking);
        }
        objectives.add(enemyAttacking);
        return this;
    }

    @Override
    public String getActionText() {
        return "Kill %s at %s".formatted(enemyAttacking.getName(), enemyAttacking.location.getName());
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of("goto", "kill"));
    }
}
