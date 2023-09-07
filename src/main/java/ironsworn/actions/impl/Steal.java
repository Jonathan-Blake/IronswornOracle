package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;

import java.util.List;

public class Steal extends QuestAction {
    private ItemData item;

    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        this.item  = objectives.getItemAcquired();
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
