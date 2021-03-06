package generics;

import static generics.Helper.sign;

public class Fraction implements IFraction, Comparable<Fraction> {
    private final float numerator;
    private final float denominator;

    public Fraction(float numerator, float denominator) throws ArithmeticException {
        if (denominator == 0) throw new ArithmeticException("Division by 0");
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public float getNumerator() {
        return numerator;
    }

    @Override
    public float getDenominator() {
        return denominator;
    }

    @Override
    public int compareTo(Fraction o) throws NullPointerException {
        if (o == null) throw new NullPointerException("compared object is null");
        float diff = this.getNumerator() * o.getDenominator() - this.getDenominator() * o.getNumerator();
        return sign(diff);
    }

    @Override
    public String toString() {
        return String.format("%f/%f", getNumerator(), getDenominator());
    }
}
