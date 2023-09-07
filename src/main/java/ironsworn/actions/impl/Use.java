package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;

public class Use extends BaseQuestAction {
    private ItemData item;

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
}
