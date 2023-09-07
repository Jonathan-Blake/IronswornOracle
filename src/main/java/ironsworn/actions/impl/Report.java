package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCData;

import java.util.List;

public class Report extends QuestAction {

    private NPCData reportingTo;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert objectives.getReportingTo() == null;
        reportingTo = storyTeller.GetFriendlyNPC();
        objectives.setReportingTo(reportingTo);
        return this;
    }

    @Override
    public String getActionText() {
        return "Report to "+reportingTo.name;
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }

}
