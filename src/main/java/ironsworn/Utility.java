package ironsworn;

import java.util.List;
import java.util.Random;

public class Utility {
    private static final Random RANDOM = new Random();

    public static <T> T pickRandom(List<T> choices) {
        return choices.get(RANDOM.nextInt(choices.size()));
    }
}
