package ironsworn.actions;

import graph.structs.QuestNode;
import ironsworn.InvalidActionException;
import ironsworn.Objective;
import ironsworn.StoryTeller;
import ironsworn.actions.impl.*;
import ironsworn.utility.Utility;

import java.util.*;

public abstract class QuestAction extends QuestNode {
    private List<QuestAction> subActions = new LinkedList<>();
    private boolean expandable = true;

    protected QuestAction(String name) {
        super(name);
    }

    public static QuestAction getFromKeyword(String keyword) {
        return switch (keyword) {
            case "explore" -> new Explore();
            case "gather" -> new Gather();
            case "get" -> new Get();
            case "give" -> new Give();
            case "goto" -> new GoTo();
            case "kill" -> new Kill();
            case "listen" -> new Listen();
            case "report" -> new Report();
            case "use" -> new Use();
            case "learn" -> new Learn();
            case "spy" -> new Spy();
            case "steal" -> new Steal();
            case "stealth" -> new Stealth();
            case "read" -> new Read();
            default -> throw new InvalidActionException(keyword);
        };
    }

    public static int countNodes(QuestAction node) {
        if (node.getSubActions().isEmpty()) {
            return 1;
        } else {
            return node.getSubActions().stream().mapToInt(QuestAction::countNodes).sum();
        }
    }

    public static StringBuilder printIndentedTree(QuestAction root, int i) {
        StringBuilder s = new StringBuilder("  ".repeat(i) + root.getActionText());
        root.getSubActions().forEach(sub -> s.append(System.lineSeparator()).append(printIndentedTree(sub, i + 1)));
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
                    System.out.println("Closing plot thread.");
                    expandable = false;
                    return false;
                } else {
                    final Objective objective = new Objective(storyTeller.campaign);
                    updateObjectives(objective);
                    objective.setExpansionOf(this);
                    final List<QuestAction> internalActions = storyTeller.assignActions(nextSteps, objective, this).getSubActions();
                    this.setSubActions(internalActions);
                    return true;
                }
            } else {
                final List<QuestAction> tmp = new ArrayList<>(this.getSubActions());
                tmp.sort(Comparator.comparing(QuestAction::countNodes));
                for (QuestAction subAction : tmp) {
                    if (subAction.expand(storyTeller)) {
                        return true;
                    }
                }
            }
            expandable = false;
            return false;
        }
}
