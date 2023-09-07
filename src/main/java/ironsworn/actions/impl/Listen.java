package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCData;

import java.util.List;

public class Listen extends QuestAction {
    private NPCData target;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        this.target = storyTeller.GetFriendlyNPC();
        return this;
    }

    @Override
    public String getActionText() {
        return "listen to %s".formatted(target.name);
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }
}
