package generic;

public class Main {

    public static void main(String[] args) {
	// write your code here

    }
    public static <T extends Comparable<T>> int compare(T a, T b) {
        return a.compareTo(b);
    }

    public static int compare(String a, String b) {
        int lenA = a.length();
        int lenB = b.length();
        if (lenA == lenB) return 0;
        return lenA > lenB ? 1 : -1;
    }
}
