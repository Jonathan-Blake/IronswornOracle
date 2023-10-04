package ironsworn.actions.impl;

import graph.search.Criteria;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCBuilder;
import ironsworn.structs.NPCData;
import ironsworn.structs.Opinion;

import java.util.List;

public class Spy extends BaseQuestAction {
    private NPCData target;

    public Spy() {
        super("spy");
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        target = storyTeller.getEnemy(Criteria.noRequirements()).orElseGet(() -> storyTeller.createNPC(new NPCBuilder().relationship(Opinion.ENEMY)));
        return this;
    }

    @Override
    public String getActionText() {
        return "spy on %s".formatted(target.getName());
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(List.of("goto","stealth","listen","goto","report"));
    }
}
