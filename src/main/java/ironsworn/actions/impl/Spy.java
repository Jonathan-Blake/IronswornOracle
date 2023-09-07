package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.EnemyData;

import java.util.List;

public class Spy extends QuestAction {
    private EnemyData target;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        target = storyTeller.GetEnemy();
        return this;
    }

    @Override
    public String getActionText() {
        return "spy on %s".formatted(target.name);
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of("goto","stealth","listen","goto","report"));
    }
}
