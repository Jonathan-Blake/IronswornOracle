package ironsworn.actions.impl;

import graph.constraints.IsDuring;
import graph.search.Criteria;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCBuilder;
import ironsworn.structs.NPCData;
import ironsworn.structs.Opinion;

import java.util.List;

public class Kill extends BaseQuestAction {
    private NPCData enemyAttacking;

    public Kill() {
        super("kill");
    }

    @Override
    public void updateObjectives(Objective objectives) {
        objectives.setEnemyAttacking(enemyAttacking);
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert (objectives.getEnemyAttacking() == null || storyTeller.campaign.getVertex(this)
                .getAdjacent().entrySet().stream()
                .filter(kv -> kv.getValue().equals(IsDuring.get()))
                .anyMatch(kv -> kv.getKey().getContents() instanceof Kill));
        if (objectives.getEnemyAttacking() != null) {
            enemyAttacking = objectives.getEnemyAttacking();
        } else {
            enemyAttacking = storyTeller.getEnemy(Criteria.noRequirements()).orElseGet(() -> storyTeller.createNPC(new NPCBuilder().relationship(Opinion.ENEMY)));
            objectives.setEnemyAttacking(enemyAttacking);
        }
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
