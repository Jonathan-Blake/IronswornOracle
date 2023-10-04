package ironsworn;

import ironsworn.actions.QuestAction;
import ironsworn.structs.ItemData;
import ironsworn.structs.NPCData;

public class Objective {
    NPCData reportingTo = null;
    private final Campaign campaign;
    ItemData itemAcquired = null;
    ItemData itemUsed = null;
    private QuestAction previousAction;
    NPCData enemyAttacking = null;

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
}
