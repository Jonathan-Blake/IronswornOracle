package ironsworn.actions.impl;

import graph.search.Criteria;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemBuilder;
import ironsworn.structs.ItemData;
import ironsworn.structs.NPCBuilder;
import ironsworn.structs.NPCData;

import static ironsworn.structs.Opinion.FRIENDLY;

public class Give extends BaseQuestAction {
    private ItemData item;
    private NPCData target;

    public Give() {
        super("give");
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert objectives.getReportingTo() == null && objectives.getItemAcquired() == null;
        item = storyTeller.getItem(Criteria.noRequirements()).orElseGet(() -> storyTeller.createItem(new ItemBuilder()));
        target = storyTeller.getFriendlyNPC(Criteria.noRequirements()).orElseGet(() -> storyTeller.createNPC(new NPCBuilder().relationship(FRIENDLY)));
        objectives.setReportingTo(target);
        objectives.setItemAcquired(item);
        return this;
    }

    @Override
    public String getActionText() {
        return "Give %s to %s at %s".formatted(item.getName(), target.getName(), target.location.getName());
    }
}
