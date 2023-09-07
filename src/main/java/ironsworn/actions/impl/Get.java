package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;

import java.util.List;

public class Get extends BaseQuestAction {
    private ItemData item;

    @Override
    public void updateObjectives(Objective objectives) {
        objectives.setItemAcquired(item);
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        if ((objectives.getItemAcquired() == null && objectives.getItemUsed() == null)) {
            throw new AssertionError();
        }
        if (objectives.getItemAcquired() != null) {
            this.item = objectives.getItemAcquired();
            objectives.setItemAcquired(null);
        } else if (objectives.getItemUsed() != null) {
            this.item = objectives.getItemUsed();
            objectives.setItemUsed(null);
        }
        return this;
    }

    @Override
    public String getActionText() {
        return "get %s".formatted(item.name);
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(
                List.of(),
                List.of("steal"),
                List.of("goto", "gather"),
                List.of("goto","get","goto","subquest","exchange")
        );
    }

}
