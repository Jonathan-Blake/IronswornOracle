package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;

import java.util.List;

public class Use extends QuestAction {
    private ItemData item;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert objectives.getItemUsed() == null;
        item = storyTeller.GetItem();
        objectives.setItemUsed(item);
        return this;
    }

    @Override
    public String getActionText() {
        return "use %s".formatted(item.name);
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }
}
