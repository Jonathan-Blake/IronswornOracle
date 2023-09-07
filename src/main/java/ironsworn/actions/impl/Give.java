package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;
import ironsworn.structs.NPCData;

import java.util.List;

public class Give extends QuestAction {
    private ItemData item;
    private NPCData target;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert objectives.getReportingTo() == null && objectives.getItemAcquired() == null;
        item = storyTeller.GetItem();
        target = storyTeller.GetFriendlyNPC();
        objectives.setReportingTo(target);
        objectives.setItemAcquired(item);
        return this;
    }

    @Override
    public String getActionText() {
        return "Give %s to %s at %s".formatted(item.name, target.name, target.location.name);
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }
}
