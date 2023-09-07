package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCData;

public class Listen extends BaseQuestAction {
    private NPCData target;

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        this.target = storyTeller.GetFriendlyNPC();
        return this;
    }

    @Override
    public String getActionText() {
        return "listen to %s".formatted(target.name);
    }
}
