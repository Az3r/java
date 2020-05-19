package test.generics;

import generics.ComplexNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComplexNumberTest {

    private static Stream<Arguments> compareProvider() {
        return Stream.of(
                Arguments.of(new ComplexNumber(1, 1), new ComplexNumber(1, 1), 0),
                Arguments.of(new ComplexNumber(2, 1), new ComplexNumber(1, 1), 1),
                Arguments.of(new ComplexNumber(1, 1), new ComplexNumber(2, 1), -1),
                Arguments.of(new ComplexNumber(5, 17), new ComplexNumber(5, 5), 1),
                Arguments.of(new ComplexNumber(5, 5), new ComplexNumber(5, 17), -1),
                Arguments.of(new ComplexNumber(15, 5), new ComplexNumber(16, 1), -1),
                Arguments.of(new ComplexNumber(16, 1), new ComplexNumber(16, 6), -1),
                Arguments.of(new ComplexNumber(15, 5), new ComplexNumber(16, 6), -1)
        );
    }

    @Test
    void propertyTest() {
        ComplexNumber cn = new ComplexNumber(5, 91);
        assertEquals(5, cn.getReal());
        assertEquals(91, cn.getImg());
    }

    @ParameterizedTest
    @MethodSource("compareProvider")
    void compareTo(ComplexNumber a, ComplexNumber b, int expected) {
        assertEquals(expected, a.compareTo(b));
    }

    @Test
    void comparedTo_Null(){
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws NullPointerException {
                ComplexNumber a = new ComplexNumber(1,2);
                a.compareTo(null);
            }
        });
    }
}