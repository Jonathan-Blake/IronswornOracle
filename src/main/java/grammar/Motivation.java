package grammar;

import java.util.List;

import static grammar.Motivation.Constants.*;

public enum Motivation {

    Knowledge("Information known to a character", List.of(
            new QuestLine("Deliver item for study", GET, GOTO, GIVE),
            new QuestLine("Spy", SPY),
            new QuestLine("Interview NPC", GOTO, LISTEN, GOTO, REPORT),
            new QuestLine("Use an item in the field", GET, GOTO, USE, GOTO, GIVE)
    )),
    Comfort("Physical comfort", List.of(
            new QuestLine("Obtain luxuries", GET, GOTO, GIVE)
//            new QuestLine("Kill pests", "goto","damage", "goto","report")
    )),
    Reputation("How others perceive a character", List.of(
            new QuestLine("Obtain Rare items", GET, GOTO, GIVE),
            new QuestLine("Kill Enemies", GOTO, KILL, GOTO, REPORT),
            new QuestLine("Visit a dangerous place", GOTO, GOTO, REPORT)
    )),
    Serenity("Peace of mind", List.of()),
    Protection("Security against threats", List.of()),
    Conquest("Desire to prevail over enemies", List.of()),
    Wealth("Economic power", List.of()),
    Ability("Character skills", List.of()),
    Equipment("Usable assets", List.of());

    public final String purpose;
    public final List<QuestLine> possibleQuestlines;

    Motivation(String purpose, List<QuestLine> possibleQuestlines) {
        this.purpose = purpose;
        this.possibleQuestlines = possibleQuestlines;
    }

    static class Constants {
        public static final String REPORT = "report";
        public static final String GIVE = "give";
        public static final String GOTO = "goto";
        public static final String USE = "use";
        public static final String LISTEN = "listen";
        public static final String GET = "get";
        public static final String SPY = "spy";
        public static final String KILL = "kill";
    }
}
