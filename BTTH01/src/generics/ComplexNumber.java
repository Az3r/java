package generics;

import static generics.Helper.sign;

public class ComplexNumber implements IComplexNumber, Comparable<ComplexNumber> {
    private final float real;
    private final float img;

    public ComplexNumber(float real, float img) {
        this.real = real;
        this.img = img;
    }

    @Override
    public float getReal() {
        return real;
    }

    @Override
    public float getImg() {
        return img;
    }

    @Override
    public int compareTo(ComplexNumber o) {
        int realDiff = sign(this.getReal() - o.getReal());
        int imgDiff = sign(this.getImg() - o.getImg());
        return realDiff != 0 ? realDiff : imgDiff;
    }
}
