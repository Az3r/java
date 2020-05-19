package test.generics;

import generics.ComplexNumber;
import generics.Fraction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FractionTest {

    private static Stream<Arguments> fractionProvider() {
        return Stream.of(
                Arguments.of(new Fraction(1, 1), new Fraction(1, 1), 0),
                Arguments.of(new Fraction(2.14f, 1), new Fraction(2.14f, 2), 1),
                Arguments.of(new Fraction(2.14f, 2), new Fraction(2.14f, 1), -1),
                Arguments.of(new Fraction(2.14f, 3), new Fraction(2.14f, 2), -1),
                Arguments.of(new Fraction(2.14f, 3), new Fraction(2.14f, 1), -1)
        );
    }

    @Test
    void getNumerator() {
        Fraction fraction = new Fraction(1.433f, 2.4343f);
        assertEquals(1.433f, fraction.getNumerator());
    }

    @Test
    void getDenominator() {
        Fraction fraction = new Fraction(1.433f, 2.4343f);
        assertEquals(2.4343f, fraction.getDenominator());
    }

    @Test
    void getDenominator_ZeroValue() {
        assertThrows(ArithmeticException.class, () -> {
            new Fraction(1, 0);
        });
    }

    @ParameterizedTest
    @MethodSource("fractionProvider")
    void compareTo(Fraction a, Fraction b, int expected) {
        assertEquals(expected, a.compareTo(b));
    }

    @Test
    void comparedTo_Null(){
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws NullPointerException {
                Fraction a = new Fraction(1,2);
                a.compareTo(null);
            }
        });
    }
}