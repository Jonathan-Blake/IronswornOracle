package ironsworn.oracle;

import ironsworn.utility.Tuple;
import ironsworn.utility.Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Oracle {
    private final SimpleLookupTable locationSuffix;
    private final NestableLookupTable locationPrefix;
    private final SimpleLookupTable surnameLookup;
    private final LookupTable<String> nameLookup;
    private final LookupTable<String> titleLookup;

    public Oracle() throws IOException {
        this.nameLookup = new SimpleLookupTable();
        this.surnameLookup = new SimpleLookupTable();
        this.titleLookup = new SimpleLookupTable();
        locationPrefix = new NestableLookupTable();
        locationSuffix = new SimpleLookupTable();
        try (BufferedReader io = new BufferedReader(new FileReader(Path.of("src/main/resources/humanNames.csv").toFile()))) {
            String nextLine = io.readLine();
            while (nextLine != null) {
                this.nameLookup.add(new Tuple<>(1, nextLine));
                nextLine = io.readLine();
            }
        }
        try (BufferedReader io = new BufferedReader(new FileReader(Path.of("src/main/resources/surname.csv").toFile()))) {
            String nextLine = io.readLine();
            while (nextLine != null) {
                this.surnameLookup.add(new Tuple<>(1, nextLine));
                nextLine = io.readLine();
            }
        }
        try (BufferedReader io = new BufferedReader(new FileReader(Path.of("src/main/resources/title.csv").toFile()))) {
            String nextLine = io.readLine();
            while (nextLine != null) {
                this.titleLookup.add(new Tuple<>(1, nextLine));
                nextLine = io.readLine();
            }
        }
        try (BufferedReader io = new BufferedReader(new FileReader(Path.of("src/main/resources/locationNamePrefix.csv").toFile()))) {
            String nextLine = io.readLine();
            while (nextLine != null) {
                locationPrefix.add(new Tuple<>(1, new SingleRowLookup(nextLine)));
                titleLookup.add(new Tuple<>(1, "the " + nextLine));
                nextLine = io.readLine();
            }
        }
        try (BufferedReader io = new BufferedReader(new FileReader(Path.of("src/main/resources/locationNameSuffix.csv").toFile()))) {
            String nextLine = io.readLine();
            while (nextLine != null) {
                locationSuffix.add(new Tuple<>(1, nextLine));
                nextLine = io.readLine();
            }
        }
        locationPrefix.add(new Tuple<>(5, nameLookup));

        //Add Blanks
        this.titleLookup.add(new Tuple<>(this.titleLookup.getMax() * 4, ""));
        this.surnameLookup.add(new Tuple<>(this.surnameLookup.getMax() * 2, ""));
    }

    public String randomName() {
        return Utility.lazyReplace(
                Utility.joinWithoutBlanks(this.nameLookup.getRandomItem().get(),
                        surnameLookup.getRandomItem().get(),
                        titleLookup.getRandomItem().get()),
                new Tuple<>("<NAME>", this::randomName),
                new Tuple<>("<LOCATION>", this::randomLocationName)
        );

    }

    public String randomLocationName() {
        return locationPrefix.getRandomItem().get()
                + locationSuffix.getRandomItem().get();
    }
}
