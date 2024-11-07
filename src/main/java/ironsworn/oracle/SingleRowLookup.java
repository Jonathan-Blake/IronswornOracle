package ironsworn.oracle;

import ironsworn.utility.Tuple;

import java.util.function.Supplier;

public class SingleRowLookup implements LookupTable<String> {
    private final String content;

    public SingleRowLookup(String contents) {
        this.content = contents;
    }

    @Override
    public String get(Integer i) {
        return content;
    }

    @Override
    public Supplier<String> getRandomItem() {
        return () -> content;
    }

    @Override
    public int getMax() {
        return 1;
    }

    @Override
    public void add(Tuple<Integer, String> entry) {
        throw new UnsupportedOperationException("Adding to single Lookup");
    }
}
