package ironsworn.oracle;

import ironsworn.utility.Tuple;

import java.util.function.Supplier;

public interface LookupTable<T> {
    T get(Integer i);

    Supplier<String> getRandomItem();

    void add(Tuple<Integer, T> entry);

    int getMax();
}
