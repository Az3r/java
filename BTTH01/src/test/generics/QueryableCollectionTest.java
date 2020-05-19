package test.generics;

import generics.QueryableCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueryableCollectionTest {

    private static Stream<Arguments> integerProvider() {
        return Stream.of(
                Arguments.of(new Integer[0], null),
                Arguments.of(new Integer[]{1, 2, 3, 4, 5}, 5),
                Arguments.of(new Integer[]{1, 2, 4, 5}, 5),
                Arguments.of(new Integer[]{5, -1, 3, 58, -6, 10, 40}, 58)
        );
    }


    @ParameterizedTest
    @MethodSource("integerProvider")
    void getMax(Integer[] collection, Integer expected) {
        QueryableCollection<Integer> queryable = new QueryableCollection<>(Arrays.asList(collection));
        assertEquals(expected, queryable.getMax());
    }

    @Test
    void getMax_NullElements() {
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                QueryableCollection<Integer> collection = new QueryableCollection<>(Arrays.asList(null, null, 1, 2, 3));
                collection.getMax();
            }
        });
    }
}