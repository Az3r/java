package collection.b;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static collection.Helper.arrayToString;
import static collection.Helper.fromString;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a list of strings:");
        String[] tokens = scanner.nextLine().split("[\\s,]+", 0);

        System.out.println("Enter 2 positions you want to swap:");
        List<Integer> pos = fromString(scanner.nextLine(), 2);

        System.out.printf("Input: %s", arrayToString(tokens));
        System.out.println();
        
        try {
            Collections.swap(Arrays.asList(tokens), pos.get(0), pos.get(1));

            System.out.printf("After: %s", arrayToString(tokens));
            System.out.println();
            scanner.close();

        } catch (IndexOutOfBoundsException e) {
            System.out.println("invalid position, program will end");
        }

    }

}
