package test.generics;

import generics.ComplexNumber;
import generics.QueryableCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryableCollectionTest {

    private static Stream<Arguments> integerProvider() {
        return Stream.of(
                Arguments.of(new Integer[]{1, 2, 3, 4, 5}, 5),
                Arguments.of(new Integer[]{1, 2, null, 4, 5}, 5),
                Arguments.of(new Integer[]{5, -1, 3, 58, -6, 10, 40}, 58)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void getMax_NullEmptyCollection(QueryableCollection<ComplexNumber> collection) {
        assertAll();
        Assertions.assertNull(collection.getMax());
    }

    @ParameterizedTest
    @MethodSource("integerProvider")
    void getMax(Integer[] collection, Integer expected) {
        QueryableCollection<Integer> queryable = new QueryableCollection<>(Arrays.asList(collection));
        assertEquals(expected, queryable.getMax());
    }
}