package ironsworn;

import grammar.Motivation;
import grammar.Quest;
import grammar.QuestLine;
import graph.constraints.IsAfter;
import graph.constraints.IsDuring;
import graph.search.Criteria;
import graph.structs.NamedCampaignItem;
import ironsworn.actions.QuestAction;
import ironsworn.actions.Subquest;
import ironsworn.structs.*;
import ironsworn.utility.Utility;

import java.util.*;

public class StoryTeller {
    public final Campaign campaign;

    public StoryTeller(Campaign campaign) {
        this.campaign = campaign;
    }
    public StoryTeller(){
        this.campaign = new Campaign();
    }

    public Quest createNewQuest(Motivation motivation, int size){
        final QuestLine strings = Utility.pickRandom(motivation.possibleQuestlines);
        Quest q = new Quest(assignActions(List.of(strings.keywords)));
        while (q.size() < size && q.expand(this)){
            System.out.println("Expanding Quest "+(q.size()));
        }
        return q;
    }

    public Subquest assignActions(final List<String> actions) {
        return assignActions(actions, new Objective(this.campaign), null);
    }

    public Subquest assignActions(final List<String> actions, final Objective objectives, QuestAction parent) {
        System.out.println("Assigning: " + actions);
        List<String> reversed = new ArrayList<>(actions);
        Collections.reverse(reversed);

        List<QuestAction> questActions = new LinkedList<>();
        for (String keyword : reversed) {
            QuestAction action = QuestAction.getFromKeyword(keyword);
            if (objectives.getPreviousGoal() != null) {
                campaign.addEdge(IsAfter.get(), action, objectives.getPreviousGoal());
            }
            if (parent != null) {
                campaign.addEdge(IsDuring.get(), parent, action);
            }
            action.initialise(objectives, this);
            objectives.setPreviousGoal(action);
            questActions.add(0, action);
        }

        return new Subquest(questActions);
    }

    public Optional<NPCData> getFriendlyNPC(Criteria<NamedCampaignItem> requirements) {
        return campaign.getFriendlyNPC(requirements);
    }

    public Optional<LocationData> getLocation(Criteria<NamedCampaignItem> requirements) {
        return (campaign.getLocation(requirements));
    }

    public Optional<NPCData> getEnemy(Criteria<NamedCampaignItem> requirements) {
        return (campaign.getEnemy(requirements));
    }

    public Optional<ItemData> getItem(Criteria<NamedCampaignItem> requirements) {
        return (campaign.getItem(requirements));
    }

    public NPCData createNPC(NPCBuilder parameters) {
        final NPCData build = parameters.build(this);
        campaign.create(() -> build, parameters.getRelationships());
        return build;
    }

    public ItemData createItem(ItemBuilder parameters) {
        final ItemData build = parameters.build(this);
        campaign.create(() -> build, parameters.getRelationships());
        return build;
    }

    public LocationData createLocation(LocationBuilder parameters) {
        final LocationData build = parameters.build(this);
        campaign.create(() -> build, parameters.getRelationships());
        return build;
    }

    public String randomName() {
        return "Fred";
    }

    public String randomLocationName() {
        return "this.LOCATION";
    }

    public String randomItemName() {
        return "Spoon";
    }
}
