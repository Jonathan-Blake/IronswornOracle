package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.LocationData;

import java.util.List;

public class GoTo extends QuestAction {

    public static final String textFormat = "Go to %s at %s";
    private String target;
    private LocationData location;
    private boolean enemy;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        if (objectives.getReportingTo() != null){
            location = objectives.getReportingTo().location;
            target = objectives.getReportingTo().name;
            enemy = false;
            objectives.setReportingTo(null);
        } else if(objectives.getEnemyAttacking()!= null){
            location = objectives.getEnemyAttacking().location;
            target = objectives.getEnemyAttacking().name;
            enemy = true;
            objectives.setEnemyAttacking(null);
        } else {
            location = storyTeller.GetLocation();
        }
        return this;
    }

    @Override
    public String getActionText() {
        return ("go to %s".formatted(location.name))+(target!= null? " and meet "+target:"");
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of(), List.of("explore"), List.of("learn","goto"));
    }
}