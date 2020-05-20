package collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static collection.Helper.fromString;

class HelperTest {

    private static Stream<Arguments> validIntProvider_short() {
        return Stream.of(
                Arguments.of(null, new Integer[0]),
                Arguments.of("", new Integer[0]),
                Arguments.of(" ", new Integer[0]),
                Arguments.of(",", new Integer[0]),
                Arguments.of(", ", new Integer[0]),
                Arguments.of("1,2,3", new Integer[]{1, 2, 3}),
                Arguments.of("1 2 3", new Integer[]{1, 2, 3}),
                Arguments.of("1, 2, 3,4 5", new Integer[]{1, 2, 3, 4, 5}),
                Arguments.of("1,,,, 2,,,  ,,3, , 4 5", new Integer[]{1, 2, 3, 4, 5}),
                Arguments.of("1   2  3", new Integer[]{1, 2, 3})
        );
    }

    private static Stream<Arguments> validIntProvider_long() {
        return Stream.of(
                Arguments.of("12 2 3 4 5", 2, new Integer[]{12, 2}),
                Arguments.of("12 a 2 3 4", 1, new Integer[]{12}),
                Arguments.of("12 a 2 3 4", 0, new Integer[0])
        );
    }

    private static Stream<Arguments> invalidIntProvider() {
        return Stream.of(
                Arguments.of("--2", Integer.MAX_VALUE),
                Arguments.of("--++2", Integer.MAX_VALUE),
                Arguments.of("12 a 2 3 4 5 6,  2,2,45,,43,-323", Integer.MAX_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("validIntProvider_long")
    void fromString_valid_long(String input, int limit, Integer[] expected) {
        List<Integer> collection = fromString(input, limit);
        Integer[] result = new Integer[collection.size()];
        collection.toArray(result);
        Assertions.assertArrayEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("validIntProvider_short")
    void fromString_valid_short(String input, Integer[] expected) {
        List<Integer> collection = fromString(input);
        Integer[] result = new Integer[collection.size()];
        collection.toArray(result);
        Assertions.assertArrayEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("invalidIntProvider")
    void fromString_invalid(String input, int limit) {
        Assertions.assertThrows(NumberFormatException.class, () -> fromString(input, limit));
    }
}