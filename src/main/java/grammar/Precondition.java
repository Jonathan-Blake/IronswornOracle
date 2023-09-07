package grammar;

import java.util.function.Predicate;

public class Precondition implements Predicate<Quest> {
    @Override
    public boolean test(Quest quest) {
        return false;
    }
}
