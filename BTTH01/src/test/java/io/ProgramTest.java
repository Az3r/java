package io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

class ProgramTest {

    private static Stream<Arguments> validArgumentProvider() {
        return Stream.of(
                Arguments.of("HelLo", null, "HelLo"),
                Arguments.of("-i", "empty-file.txt", ""),
                Arguments.of("-i", " normal-file.txt", "ABCDEFGHJKL"),
                Arguments.of("abcd,./12345", "-i", "abcd,./12345")
        );
    }

    private static Stream<Arguments> invalidArgumentProvider() {
        return Stream.of(
                Arguments.of(null, null, NullPointerException.class),
                Arguments.of(null, "hello", NullPointerException.class),
                Arguments.of("-i", "not-existed-file.txt", NoSuchFieldException.class),
                Arguments.of("-o", "unimportant-params", IllegalArgumentException.class),
                Arguments.of("-d", null, IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("validArgumentProvider")
    void getInput_valid(String arg1, String arg2, String expected) throws IOException {
        Assertions.assertEquals(expected, CommandLineProgram.getInput(arg1, arg2));
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentProvider")
    void getInput_invalid(String arg1, String arg2, Class<? extends Throwable> expected) {
        Assertions.assertThrows(expected, () -> CommandLineProgram.getInput(arg1, arg2));
    }
}