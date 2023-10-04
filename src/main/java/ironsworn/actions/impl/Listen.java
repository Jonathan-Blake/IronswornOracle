package ironsworn.actions.impl;

import graph.search.Criteria;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCBuilder;
import ironsworn.structs.NPCData;

import static ironsworn.structs.Opinion.FRIENDLY;

public class Listen extends BaseQuestAction {
    private NPCData target;

    public Listen() {
        super("listen");
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        this.target = storyTeller.getFriendlyNPC(Criteria.noRequirements()).orElseGet(() -> storyTeller.createNPC(new NPCBuilder().relationship(FRIENDLY)));
        return this;
    }

    @Override
    public String getActionText() {
        return "listen to %s".formatted(target.getName());
    }
}
