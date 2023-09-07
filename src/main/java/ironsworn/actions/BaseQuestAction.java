package ironsworn.actions;

import ironsworn.Objective;

import java.util.List;

public abstract class BaseQuestAction extends QuestAction {
    @Override
    public void updateObjectives(Objective objectives) {

    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }
}
