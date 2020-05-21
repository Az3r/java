package collection.c;

import java.util.*;

import static collection.Helper.arrayToString;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a list of strings:");
        String[] tokens = scanner.nextLine().split("[\\s,]+", 0);

        System.out.printf("Input: %s", arrayToString(tokens));
        System.out.println();

        List<String> output = new ArrayList<>(new HashSet<>(Arrays.asList(tokens)));
        System.out.printf("After: %s", arrayToString(output));
        System.out.println();
    }

}
