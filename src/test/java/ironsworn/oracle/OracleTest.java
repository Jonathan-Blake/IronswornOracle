package ironsworn.oracle;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class OracleTest {

    @Test
    void randomName() throws IOException {
        Oracle o = new Oracle();
        int fails = 0;
        for (int i = 0; i < 100; i++) {
            try {
                System.out.println(o.randomName());
            } catch (StackOverflowError e) {
                fails++;
            }
        }
        System.out.println();
        System.out.println("Errors: " + fails);
    }

    @Test
    void randomLocation() throws IOException {
        Oracle o = new Oracle();
        for (int i = 0; i < 100; i++) {
            System.out.println(o.randomLocationName());
        }
    }
}