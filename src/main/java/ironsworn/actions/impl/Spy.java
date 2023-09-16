package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.EnemyData;

import java.util.List;

public class Spy extends BaseQuestAction {
    private EnemyData target;

    public Spy() {
        super("spy");
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
