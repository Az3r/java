package generics;

public class Helper {
    private Helper() {
    }

    public static int sign(float num) {
        if (num > 0) return 1;
        if (num < 0) return -1;
        return 0;
    }
}
