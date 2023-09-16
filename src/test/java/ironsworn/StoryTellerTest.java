package ironsworn;

import grammar.Motivation;
import grammar.Quest;
import ironsworn.actions.Subquest;
import ironsworn.structs.EnemyData;
import ironsworn.structs.ItemData;
import ironsworn.structs.LocationData;
import ironsworn.structs.NPCData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoryTellerTest {

    @Mock
    private Campaign mockCampaign;

    @Test
    void assignActions() {
        StoryTeller storyTeller = new StoryTeller(mockCampaign);
        when(mockCampaign.getEnemy()).thenReturn(new EnemyData("Bob", new LocationData("BobTown"), null));
        when(mockCampaign.getFriendlyNPC()).thenReturn(new NPCData("Steve", new LocationData("Tutorial Village")));

        Subquest ret = storyTeller.assignActions(List.of("goto", "kill", "goto", "report"));

        System.out.println(ret.getActionText());
    }

    @Test
    void createNewQuest() {
        StoryTeller storyTeller = new StoryTeller(mockCampaign);
        Mockito.lenient().when(mockCampaign.getEnemy()).thenReturn(new EnemyData("Bob", new LocationData("BobTown"), null));
        Mockito.lenient().when(mockCampaign.getFriendlyNPC()).thenReturn(new NPCData("Steve", new LocationData("Tutorial Village")));
        Mockito.lenient().when(mockCampaign.getLocation()).thenReturn(new LocationData("Peaceful Waterfall"));
        Mockito.lenient().when(mockCampaign.getItem()).thenReturn(new ItemData("Thing-a-majig"));

        Quest ret = storyTeller.createNewQuest(Motivation.Knowledge, 10);

        System.out.println(ret.size());
        System.out.println(ret.getFullText());
        assertTrue(ret.size() >= 6, "Expected size to be greater than 5 but was  " + ret.size());
    }
}