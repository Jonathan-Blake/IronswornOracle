import grammar.Motivation;
import grammar.Quest;
import ironsworn.Campaign;
import ironsworn.StoryTeller;

public class ScratchRunner {
    public static void main(String[] args) {
        Campaign c = new Campaign();
        StoryTeller storyTeller = new StoryTeller(c);
        Quest q = storyTeller.createNewQuest(Motivation.Knowledge, 5);
        System.out.println(q.getFullText());
    }
}
