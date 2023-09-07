package grammar;

import ironsworn.StoryTeller;
import ironsworn.actions.QuestAction;
import ironsworn.actions.Subquest;

public class Quest {
    private final Subquest root;

    public Quest(Subquest root) {
        this.root = root;
    }

    public boolean expand(StoryTeller storyTeller) {
        return root.expand(storyTeller);
    }

    public int size() {
        return QuestAction.countNodes(root);
    }

    public String getActionText() {
        return root.getActionText();
    }
    public String getFullText(){
        return QuestAction.printIndentedTree(root, 0).toString();
    }
}
