package ironsworn.actions;

import ironsworn.Objective;
import ironsworn.StoryTeller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Subquest extends BaseQuestAction {
    private final List<QuestAction> actions;
    private boolean expandable = true;

    public Subquest(List<QuestAction> questActions) {
        this.actions = questActions;
    }

    @Override
    public boolean expand(StoryTeller storyTeller) {
        if (expandable) {
            ArrayList<QuestAction> tmp = new ArrayList<>(actions);
//            tmp.removeIf(action -> !action.isExpandable());
            tmp.sort(Comparator.comparing(QuestAction::countNodes));// Expand smallest first wide shallow trees
            // Collections.shuffle(tmp); // Randomly expand
            // tmp.sort(Comparator.comparing(QuestAction::countNodes)); // deep
            for (QuestAction questAction : tmp) {
                if (questAction.expand(storyTeller)) {
                    return true;
                }
            }
        }
        expandable = false;
        return false;
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        return null;
    }

    @Override
    public String getActionText() {
        return this.actions.stream().map(QuestAction::getActionText).collect(Collectors.joining(", then "));
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of());
    }

    @Override
    public List<QuestAction> getSubActions() {
        return this.actions;
    }
}
