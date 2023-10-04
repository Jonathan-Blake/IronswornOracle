package ironsworn.actions.impl;

import graph.search.Criteria;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemBuilder;
import ironsworn.structs.ItemData;

public class Use extends BaseQuestAction {
    private ItemData item;

    public Use() {
        super("use");
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        assert objectives.getItemUsed() == null;
        item = storyTeller.getItem(Criteria.noRequirements()).orElseGet(() -> storyTeller.createItem(new ItemBuilder()));
        objectives.setItemUsed(item);
        return this;
    }

    @Override
    public String getActionText() {
        return "use %s".formatted(item.getName());
    }
}
