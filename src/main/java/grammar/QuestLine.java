package grammar;

public class QuestLine {
    public final String strategy;
    public final String[] keywords;

    public QuestLine(String strategy, String... keywords) {
        this.strategy = strategy;
        this.keywords = keywords;
    }
}
