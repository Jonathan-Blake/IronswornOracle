package ironsworn;

import graph.structs.NamedCampaignItem;
import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;
import ironsworn.structs.NPCData;

import java.util.Deque;
import java.util.LinkedList;

public class Objective {
    NPCData reportingTo = null;
    private final Campaign campaign;
    ItemData itemAcquired = null;
    ItemData itemUsed = null;
    private QuestAction previousAction;
    NPCData enemyAttacking = null;
    Deque<NamedCampaignItem> stack = new LinkedList<>();
    private QuestAction expansionOf;

    public NamedCampaignItem peek() {
        return stack.peek();
    }

    public NamedCampaignItem pop() {
        return stack.peek();
    }

    public void add(NamedCampaignItem e) {
        stack.push(e);
    }

    public Objective(Campaign campaign) {
        this.campaign = campaign;
    }

    public NPCData getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(NPCData reportingTo) {
        this.reportingTo = reportingTo;
    }

    public NPCData getEnemyAttacking() {
        return enemyAttacking;
    }

    public void setEnemyAttacking(NPCData enemyAttacking) {
        this.enemyAttacking = enemyAttacking;
    }

    public ItemData getItemAcquired() {
        return itemAcquired;
    }

    public void setItemAcquired(ItemData itemAcquired) {
        this.itemAcquired = itemAcquired;
    }

    public ItemData getItemUsed() {
        return itemUsed;
    }

    public void setItemUsed(ItemData itemUsed) {
        this.itemUsed = itemUsed;
    }

    public void setPreviousGoal(QuestAction action) {
        previousAction = action;
    }

    public QuestAction getPreviousGoal() {
        return previousAction;
    }

    public Campaign campaign() {
        return this.campaign;
    }

    public QuestAction isExpansion() {
        return this.expansionOf;
    }

    public void setExpansionOf(QuestAction expansionOf) {
        this.expansionOf = expansionOf;
    }
}
