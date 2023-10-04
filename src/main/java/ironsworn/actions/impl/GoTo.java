package ironsworn.actions.impl;

import graph.search.Criteria;
import graph.search.impl.CampaignObjectCriteria;
import graph.search.impl.LocationCriteria;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.LocationBuilder;
import ironsworn.structs.LocationData;

import java.util.List;

public class GoTo extends BaseQuestAction {

    private String target;
    private LocationData location;
    private boolean enemy;

    public GoTo() {
        super("goto");
    }

    @Override
    public void updateObjectives(Objective objectives) {
        if (target != null) {
            if (enemy) {
                objectives.setEnemyAttacking(objectives.campaign().getEnemy(Criteria.allOf(
                        CampaignObjectCriteria.hasName(target),
                        LocationCriteria.contentsOfLocation(location)).build()
                ).orElseThrow());
            } else {
                objectives.setReportingTo(objectives.campaign().getFriendlyNPC(Criteria.allOf(
                        CampaignObjectCriteria.hasName(target),
                        LocationCriteria.contentsOfLocation(location)).build()
                ).orElseThrow());
            }
        }
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        if (objectives.getReportingTo() != null) {
            location = objectives.getReportingTo().location;
            target = objectives.getReportingTo().getName();
            enemy = false;
            objectives.setReportingTo(null);
        } else if (objectives.getEnemyAttacking() != null) {
            location = objectives.getEnemyAttacking().location;
            target = objectives.getEnemyAttacking().getName();
            enemy = true;
            objectives.setEnemyAttacking(null);
        } else {
            location = storyTeller.getLocation(Criteria.noRequirements()).orElseGet(() -> storyTeller.createLocation(new LocationBuilder()));
        }
        return this;
    }

    @Override
    public String getActionText() {
        return ("go to %s".formatted(location.getName())) + (target != null ? " and meet " + target : "");
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(
                List.of(),
                List.of("explore"),
                List.of("learn", "goto"));
    }
}
