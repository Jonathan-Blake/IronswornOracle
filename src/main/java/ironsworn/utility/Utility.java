package ironsworn.utility;

import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.function.Supplier;

public class Utility {
    private static final Random RANDOM = new Random();

    private Utility() {
    }

    public static <T> T pickRandom(List<T> choices) {
        return choices.get(RANDOM.nextInt(choices.size()));
    }

    public static String lazyReplace(String source, Tuple<String, Supplier<String>>... replacements) {
        for (Tuple<String, Supplier<String>> replacement : replacements) {
            if (source.contains(replacement.a())) {
                source = source.replace(replacement.a(), replacement.b().get());
            }
        }
        return source;
    }

    public static String joinWithoutBlanks(String... strings) {
        StringJoiner joiner = new StringJoiner(" ");
        for (String each : strings) {
            if (!each.isBlank()) {
                joiner.add(each);
            }
        }
        return joiner.toString();
    }
}
