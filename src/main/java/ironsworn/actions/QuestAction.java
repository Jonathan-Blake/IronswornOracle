package ironsworn.actions;

import ironsworn.InvalidActionException;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.Utility;
import ironsworn.actions.impl.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class QuestAction {
    private List<QuestAction> subActions = new LinkedList<>();
    private boolean expandable = true;

    public static QuestAction getFromKeyword(String keyword){
        switch (keyword){
//            case "Defend": return new Defend();
            case "report": return new Report();
            case "goto": return new GoTo();
            case "kill": return new Kill();
            case "give": return new Give();
            case "listen": return new Listen();
            case "get": return new Get();
            case "spy": return new Spy();
            case "use": return new Use();
            case "learn": return new Learn();
            case "explore": return new Explore();
            case "steal": return new Steal();
            case "gather": return new Gather();
            case "stealth": return new Stealth();
        }
        throw new InvalidActionException(keyword);
    }

    public static int countNodes(QuestAction node) {
        if(node.getSubActions().isEmpty()){
//            System.out.println("Is Empty");
            return 1;
        } else {
            return node.getSubActions().stream().mapToInt(QuestAction::countNodes).sum();
        }
    }

    public static StringBuilder printIndentedTree(QuestAction root, int i) {
        StringBuilder s = new StringBuilder("  ".repeat(i) + root.getActionText());
        root.getSubActions().forEach(sub -> {
            s.append(System.lineSeparator()).append(printIndentedTree(sub, i+1));
        });
        return s;
    }

    public abstract void updateObjectives(Objective objectives);

    public abstract QuestAction initialise(Objective objectives, StoryTeller storyTeller);

    public abstract String getActionText();
    public List<QuestAction> getSubActions(){
        return Collections.unmodifiableList(subActions);
    }
    protected void setSubActions(List<QuestAction> actions){
        subActions = actions;
    }
    protected abstract List<List<String>> getExpansions();

    public boolean expand(StoryTeller storyTeller){
            if(!expandable){
                return false;
            }
            if(this.getSubActions().isEmpty()){
                List<String> nextSteps = Utility.pickRandom(getExpansions());
                if(nextSteps.isEmpty()) {
                    //No additional steps. Try and expand a different node
                    expandable = false;
                    return false;
                } else {
                    final Objective objective = new Objective();
                    updateObjectives(objective);
                    this.setSubActions(storyTeller.assignActions(nextSteps, objective).getSubActions());
                    return true;
                }
            } else {
                for (QuestAction subAction : this.getSubActions()) {
                    if(subAction.expand(storyTeller)){
                        return true;
                    }
                }
            }
            expandable = false;
            return false;
        }
}
