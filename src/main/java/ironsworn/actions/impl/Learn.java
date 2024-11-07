package ironsworn.actions.impl;

import graph.search.Criteria;
import graph.search.impl.CampaignObjectCriteria;
import graph.structs.NamedCampaignItem;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.BaseQuestAction;
import ironsworn.actions.QuestAction;
import ironsworn.structs.NPCBuilder;

import java.util.List;

import static ironsworn.structs.Opinion.FRIENDLY;

public class Learn extends BaseQuestAction {
    private String subject;

    public Learn() {
        super("learn");
    }

    @Override
    public QuestAction initialise(Objective objectives, StoryTeller storyTeller) {
        final NamedCampaignItem target = objectives.pop();
        this.subject = storyTeller.getFriendlyNPC(
                Criteria.anyOf(CampaignObjectCriteria.hasName(target.getName())).build()
        ).orElseGet(() -> storyTeller.createNPC(new NPCBuilder().name(target.getName()).relationship(FRIENDLY))).getName();
        return this;
    }

    @Override
    public String getActionText() {
        return "learn about " + subject;
    }

    @Override
    protected List<List<String>> getExpansions() {
        return List.of(
                List.of()
//                List.of("goto", "subquest", "listen"),
//                List.of("goto", "get", "read"),
//                List.of("get", "subquest", "give", "listen")
        );
    }
}
