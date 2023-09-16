package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;
import ironsworn.structs.NPCData;

public class Give extends BaseQuestAction {
    private ItemData item;
    private NPCData target;

    public Give() {
        super("give");
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
        return "Give %s to %s at %s".formatted(item.name, target.name, target.location.getName());
    }
}
