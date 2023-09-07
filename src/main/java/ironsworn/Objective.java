package ironsworn;

import ironsworn.actions.QuestAction;
import ironsworn.structs.EnemyData;
import ironsworn.structs.ItemData;
import ironsworn.structs.NPCData;

public class Objective {
    NPCData reportingTo = null;
    EnemyData enemyAttacking = null;
    ItemData itemAcquired = null;
    ItemData itemUsed = null;
    private QuestAction previousAction;

    public NPCData getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(NPCData reportingTo) {
        this.reportingTo = reportingTo;
    }

    public EnemyData getEnemyAttacking() {
        return enemyAttacking;
    }

    public void setEnemyAttacking(EnemyData enemyAttacking) {
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
}
