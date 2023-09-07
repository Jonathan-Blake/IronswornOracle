package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.LocationData;

import java.util.List;

public class  Explore extends QuestAction {
    private LocationData location;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        if(objectives.getReportingTo() != null){
            location = objectives.getReportingTo().location;
        } else if( objectives.getEnemyAttacking() != null){
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

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }
}
