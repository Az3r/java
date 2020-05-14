package generic;

public class Complex implements Comparable<Complex>  {

    private float a;
    private float b;

    public Complex(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    @Override
    public int compareTo(Complex o) {
        return 0;
    }
}
