public final class Scratch {

    public static void main(String[] args) {
        String str = "Code point of A is 65";
        int symbol = 65;

        int found = str.indexOf(symbol, 0);
        int notFound = str.indexOf(symbol, found + 1);

        System.out.println(found); // output: 14
        System.out.println(notFound); // output: -1
    }

}