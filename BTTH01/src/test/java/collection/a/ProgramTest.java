package collection.a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProgramTest {

    @Test
    void fromString_valid() {
        List<Integer> collection = Program.fromString("12 2 3 4 5 6  2,2,45,,43,-323");
        Integer[] result = new Integer[collection.size()];
        collection.toArray(result);
        Assertions.assertArrayEquals(new Integer[]{12, 2, 3, 4, 5, 6, 2, 2, 45, 43, -323}, result);
    }

    @Test
    void fromString_invalid() {
        Assertions.assertThrows(NumberFormatException.class, () -> Program.fromString("12 a 3 4 5 6  2,2,45,,43,-323"));

    }
}