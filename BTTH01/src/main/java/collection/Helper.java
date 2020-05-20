package collection;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Helper {
    public static String arrayToString(List collection) {
        StringBuilder str = new StringBuilder();
        for (Object o : collection) {
            str.append(String.format("%s, ", o.toString()));
        }
        return str.toString();
    }

    public static <T> String arrayToString(T[] collection) {
        StringBuilder str = new StringBuilder();
        for (Object o : collection) {
            str.append(String.format("%s, ", o.toString()));
        }
        return str.toString();
    }

    public static List<Integer> fromString(String input) {
        if (input == null || input.length() == 0) return Collections.emptyList();
        String[] tokens = input.split("[\\s,]+", 0);
        Stream<Integer> ints = Stream.of(tokens).map(Integer::parseInt);
        return ints.collect(Collectors.toList());
    }

    public static List<Integer> fromString(String input, int count) {
        if (input == null || count <= 0 || input.length() == 0) return Collections.emptyList();
        String[] tokens = input.split("[\\s,]+", count + 1);
        Stream<Integer> ints = Stream.of(tokens).limit(count).map(Integer::parseInt);
        return ints.collect(Collectors.toList());
    }
}
