package collection.a;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static collection.Helper.arrayToString;
import static collection.Helper.fromString;

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

}
