package generics;

public class MyCustomString implements CharSequence, Comparable<CharSequence> {

    private final String str;

    public MyCustomString(String str) {
        this.str = str;
    }

    @Override
    public int length() {
        return str.length();
    }

    @Override
    public char charAt(int index) {
        return str.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return str.subSequence(start, end);
    }

    @Override
    public int compareTo(CharSequence o) {
        return Helper.sign(this.length() - o.length());
    }
}
