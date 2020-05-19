package test.generics;

import generics.MyCustomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class MyCustomStringTest {

    private static Stream<Arguments> stringProvider() {
        return Stream.of(
                Arguments.of(null, null, 0),
                Arguments.of(new MyCustomString(""), new MyCustomString(""), 0),
                Arguments.of(new MyCustomString(""), null, 1),
                Arguments.of(null, new MyCustomString(""), -1),
                Arguments.of(new MyCustomString("string"), new MyCustomString(""), 1),
                Arguments.of(new MyCustomString(""), new MyCustomString("string"), -1),
                Arguments.of(new MyCustomString("a"), new MyCustomString("b"), 0),
                Arguments.of(new MyCustomString("abc"), new MyCustomString("a"), 1),
                Arguments.of(new MyCustomString("abc"), new MyCustomString("abcd"), -1)
        );
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void compareTo(MyCustomString a, MyCustomString b, int expected) {
        Assertions.assertEquals(expected, a.compareTo(b));
    }
}