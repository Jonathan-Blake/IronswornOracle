package ironsworn.actions.impl;

import graph.search.Criteria;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCBuilder;
import ironsworn.structs.NPCData;

import static ironsworn.structs.Opinion.FRIENDLY;

public class Report extends BaseQuestAction {
    private NPCData reportingTo;

    public Report() {
        super("report");
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert objectives.getReportingTo() == null;
        reportingTo = storyTeller.getFriendlyNPC(Criteria.noRequirements()).orElseGet(() -> storyTeller.createNPC(new NPCBuilder().relationship(FRIENDLY)));
        objectives.setReportingTo(reportingTo);
        return this;
    }

    @Override
    public String getActionText() {
        return "Report to " + reportingTo.getName();
    }
}
