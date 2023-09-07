package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.LocationData;

public class Explore extends BaseQuestAction {
    private LocationData location;

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        if (objectives.getReportingTo() != null) {
            location = objectives.getReportingTo().location;
        } else if (objectives.getEnemyAttacking() != null) {
            location = objectives.getEnemyAttacking().location;
        } else {
            location = storyTeller.GetLocation();
        }
        return this;
    }

    @Override
    public String getActionText() {
        return "explore %s".formatted(location.name);
    }
}
