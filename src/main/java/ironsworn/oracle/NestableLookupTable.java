package ironsworn.oracle;

import ironsworn.utility.Tuple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class NestableLookupTable implements LookupTable<LookupTable> {
    public static final Random RANDOM = new Random();
    List<Tuple<Integer, LookupTable>> contents = new ArrayList<>();
    private int max;

    public LookupTable get(Integer i) {
        Iterator<Tuple<Integer, LookupTable>> itr = contents.iterator();
        Tuple<Integer, LookupTable> ret = null;
        while (i >= 0) {
            ret = itr.next();
            i -= ret.a();
        }
        assert ret != null;
        return ret.b();
    }

    @Override
    public Supplier<String> getRandomItem() {
        return () -> (String) get(RANDOM.nextInt(getMax())).getRandomItem().get();
    }

    @Override
    public void add(Tuple<Integer, LookupTable> entry) {
        assert entry.b() != this;
        contents.add(entry);
        max += entry.a();
    }

    @Override
    public int getMax() {
        return this.max;
    }
}
