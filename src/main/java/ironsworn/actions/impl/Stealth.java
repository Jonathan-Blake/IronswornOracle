package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;

public class Stealth extends BaseQuestAction {
    private String target;

    public Stealth() {
        super("stealth");
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        if (objectives.getEnemyAttacking() != null) {
            target = objectives.getEnemyAttacking().name;
        }
        return this;
    }

    @Override
    public String getActionText() {
        return "sneak up on "+target;
    }
}
