package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;

import java.util.List;

public class Gather extends QuestAction {
    private ItemData item;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        item = objectives.getItemAcquired();
        objectives.setItemAcquired(null);
        return this;
    }

    @Override
    public String getActionText() {
        return "gather %s".formatted(item.name);
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }
}
