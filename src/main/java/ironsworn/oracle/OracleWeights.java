package ironsworn.oracle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OracleWeights {
    List<Map<String, Integer>> lookupTables;

    public OracleWeights(OracleWeights a, OracleWeights b) {
        lookupTables = new ArrayList<>(a.lookupTables);
        lookupTables.addAll(b.lookupTables);
    }

    public OracleWeights append(OracleWeights other) {
        return new OracleWeights(this, other);
    }
}
