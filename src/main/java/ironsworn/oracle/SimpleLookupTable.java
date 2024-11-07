package ironsworn.oracle;

import ironsworn.utility.Tuple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SimpleLookupTable implements LookupTable<String> {
    public static final Random RANDOM = new Random();
    List<Tuple<Integer, String>> contents = new ArrayList<>();
    private int max;

    @Override
    public String get(Integer i) {
        Iterator<Tuple<Integer, String>> itr = contents.iterator();
        Tuple<Integer, String> ret = null;
        while (i >= 0) {
            ret = itr.next();
            i -= ret.a();
        }
        assert ret != null;
        return ret.b();
    }

    @Override
    public Supplier<String> getRandomItem() {
        return () -> get(RANDOM.nextInt(getMax()));
    }

    @Override
    public void add(Tuple<Integer, String> entry) {
        contents.add(entry);
        max += entry.a();
    }

    @Override
    public int getMax() {
        return this.max;
    }
}
