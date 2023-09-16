package ironsworn;

import grammar.Motivation;
import grammar.Quest;
import grammar.QuestLine;
import graph.constraints.IsAfter;
import ironsworn.actions.QuestAction;
import ironsworn.actions.Subquest;
import ironsworn.structs.EnemyData;
import ironsworn.structs.ItemData;
import ironsworn.structs.LocationData;
import ironsworn.structs.NPCData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
        return assignActions(actions, new Objective());
    }

    public Subquest assignActions(final List<String> actions, final Objective objectives) {
        System.out.println("Assigning: " + actions);
        List<String> reversed = new ArrayList<>(actions);
        Collections.reverse(reversed);

        List<QuestAction> questActions = new LinkedList<>();
        for (String keyword : reversed) {
            QuestAction action = QuestAction.getFromKeyword(keyword)
                    .initialise(objectives, this);
            if (objectives.getPreviousGoal() != null) {
                campaign.addEdge(IsAfter.get(), action, objectives.getPreviousGoal());
            }
            objectives.setPreviousGoal(action);
            questActions.add(0, action);
        }

        return new Subquest(questActions);
    }

    public NPCData GetFriendlyNPC() {
        return campaign.getFriendlyNPC();
    }

    public LocationData GetLocation() {
        return campaign.getLocation();
    }

    public EnemyData GetEnemy() {
        return campaign.getEnemy();
    }

    public ItemData GetItem() {
        return campaign.getItem();
    }
}
