package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;

import java.util.List;

public class Stealth extends QuestAction {
    private String target;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        if(objectives.getEnemyAttacking() != null){
            target = objectives.getEnemyAttacking().name;
        }
        return this;
    }

    @Override
    public String getActionText() {
        return "sneak up on "+target;
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }
}
