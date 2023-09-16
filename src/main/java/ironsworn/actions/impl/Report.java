package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCData;

public class Report extends BaseQuestAction {
    private NPCData reportingTo;

    public Report() {
        super("report");
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
}
