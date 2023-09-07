package grammar;

import java.util.List;

public enum Motivation {

    Knowledge ("Information known to a character", List.of(
            new QuestLine("Deliver item for study","get","goto", "give" ),
            new QuestLine("Spy","spy" ),
            new QuestLine("Interview NPC","goto","listen", "goto", "report" ),
            new QuestLine("Use an item in the field","get","goto", "use", "goto", "give" )
    ) ),
    Comfort ("Physical comfort", List.of() ),
    Reputation ("How others perceive a character", List.of()),
    Serenity ("Peace of mind", List.of()),
    Protection ("Security against threats",List.of() ),
    Conquest ("Desire to prevail over enemies", List.of()),
    Wealth ("Economic power", List.of()),
    Ability ("Character skills", List.of()),
    Equipment ("Usable assets", List.of());

    public final String purpose;
    public final List<QuestLine> possibleQuestlines;

    Motivation(String purpose, List<QuestLine> possibleQuestlines) {
        this.purpose = purpose;
        this.possibleQuestlines = possibleQuestlines;
    }
}
