package generics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MyCustomStringTest {

    private static Stream<Arguments> stringProvider() {
        return Stream.of(
                Arguments.of(new MyCustomString(""), new MyCustomString(""), 0),
                Arguments.of(new MyCustomString("string"), new MyCustomString(""), 1),
                Arguments.of(new MyCustomString(""), new MyCustomString("string"), -1),
                Arguments.of(new MyCustomString("a"), new MyCustomString("b"), 0),
                Arguments.of(new MyCustomString("abc"), new MyCustomString("a"), 1),
                Arguments.of(new MyCustomString("abc"), new MyCustomString("abcd"), -1),
                Arguments.of(new MyCustomString("abcd"), new MyCustomString("abcdefgh"), -1),
                Arguments.of(new MyCustomString("abc"), new MyCustomString("abcdefgh"), -1)
        );
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void compareTo(MyCustomString a, MyCustomString b, int expected) {
        Assertions.assertEquals(expected, a.compareTo(b));
    }

    @Test
    void comparedTo_Null() {
        assertThrows(NullPointerException.class, () -> {
            MyCustomString a = new MyCustomString("aadadadd");
            a.compareTo(null);
        });
    }

}