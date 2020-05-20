package collection.a;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {

    public static void main(String[] args) {
        System.out.println("Nhap day so can sap xep, vi du: 12 5 7 9 5 2 42323");
        Scanner scanner = new Scanner(System.in);

        List<Integer> collection = fromString(scanner.nextLine());

        System.out.println("Input:");
        System.out.println(arrayToString(collection));
        System.out.println();

        System.out.println("Collection after sorting");
        long start = System.currentTimeMillis();
        collection.sort(Integer::compareTo);
        long end = System.currentTimeMillis();
        System.out.println(arrayToString(collection));
        System.out.printf("Elapsed time: %d milliseconds", end - start);
        scanner.close();
    }

    private static String arrayToString(List collection) {
        StringBuilder str = new StringBuilder();
        for (Object o : collection) {
            str.append(String.format("%s, ", o.toString()));
        }
        return str.toString();
    }

    public static List<Integer> fromString(String input) {
        if (input == null) return Collections.emptyList();
        String[] tokens = input.split("[\\s,]+");
        Stream<Integer> ints = Stream.of(tokens).map(Integer::parseInt);
        return ints.collect(Collectors.toList());
    }
}
