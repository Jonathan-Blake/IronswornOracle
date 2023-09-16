package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;

import java.util.List;

public class Steal extends BaseQuestAction {
    private ItemData item;

    public Steal() {
        super("steal");
    }

    @Override
    public void updateObjectives(Objective objectives) {
        objectives.setItemAcquired(item);
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        this.item = objectives.getItemAcquired();
        objectives.setItemAcquired(null);
        return this;
    }

    @Override
    public String getActionText() {
        return "steal "+item.name;
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(
                List.of("goto","stealth","take"),
                List.of("goto","kill","take")
        );
    }
}
