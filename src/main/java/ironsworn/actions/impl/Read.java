package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;

public class Read extends BaseQuestAction {
    private ItemData target;

    public Read() {
        super("read");
    }

    @Override
    public void updateObjectives(Objective objectives) {
        objectives.setItemAcquired(target);
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        this.target = storyTeller.GetItem();
        objectives.setItemAcquired(this.target);
        return this;
    }

    @Override
    public String getActionText() {
        return "read to %s".formatted(target.name);
    }
}
