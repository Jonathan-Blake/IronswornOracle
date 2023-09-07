package ironsworn.actions.impl;

import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;

import java.util.List;

public class Learn extends BaseQuestAction {
    private String subject;

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        this.subject = storyTeller.GetFriendlyNPC().name;
        return this;
    }

    @Override
    public String getActionText() {
        return "learn about "+subject;
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(
                List.of(),
                List.of("goto", "subquest", "listen"),
                List.of("goto", "get", "read"),
                List.of("get", "subquest", "give", "listen")
        );
    }
}
