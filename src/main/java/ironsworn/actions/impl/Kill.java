package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.EnemyData;

import java.util.List;

public class Kill extends BaseQuestAction {
    private EnemyData enemyAttacking;

    public Kill() {
        super("kill");
    }

    @Override
    public void updateObjectives(Objective objectives) {
        objectives.setEnemyAttacking(enemyAttacking);
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert objectives.getEnemyAttacking() == null;
        enemyAttacking = storyTeller.GetEnemy();
        objectives.setEnemyAttacking(enemyAttacking);
        return this;
    }

    @Override
    public String getActionText() {
        return "Kill %s at %s".formatted(enemyAttacking.name, enemyAttacking.location.getName());
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of("goto", "kill"));
    }
}
