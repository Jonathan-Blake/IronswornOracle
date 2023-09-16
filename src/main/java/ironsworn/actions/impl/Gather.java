package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;

public class Gather extends BaseQuestAction {
    private ItemData item;

    public Gather() {
        super("gather");
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert (objectives.getItemAcquired() != null || objectives.getItemUsed() != null);
        if (objectives.getItemAcquired() != null) {
            item = objectives.getItemAcquired();
            objectives.setItemAcquired(null);
        } else if (objectives.getItemUsed() != null) {
            item = objectives.getItemUsed();
            objectives.setItemUsed(null);
        }
        return this;
    }

    @Override
    public String getActionText() {
        return "gather %s".formatted(item.name);
    }

}
