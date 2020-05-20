package generics;


import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {

        QueryableCollection<Integer> ints = integers();
        QueryableCollection<Float> reals = floats();

        System.out.println("Beginning to test collection of Integers...");
        System.out.print("Input: ");
        System.out.println(print(ints));
        System.out.printf("Max elements: %s\r\n", ints.getMax().toString());
        System.out.println();

        System.out.println("Beginning to test collection of Floats...");
        System.out.print("Input: ");
        System.out.println(print(reals));
        System.out.printf("Max elements: %s\r\n", reals.getMax().toString());
        System.out.println();

        System.out.println("Beginning to test collection of ComplexNumber...");
        System.out.print("Input: ");
        System.out.println(print(complexNumbers()));
        System.out.printf("Max elements: %s\r\n", complexNumbers().getMax().toString());
        System.out.println();

        System.out.println("Beginning to test collection of Fraction...");
        System.out.print("Input: ");
        System.out.println(print(fractions()));
        System.out.printf("Max elements: %s\r\n", fractions().getMax().toString());
        System.out.println();

        System.out.println("Beginning to test collection of String...");
        System.out.print("Input: ");
        System.out.println(print(strings()));
        System.out.printf("Max elements: %s\r\n", strings().getMax().toString());
    }


    private static QueryableCollection<Integer> integers() {
        int max = Math.abs(random.nextInt());
        int size = 10;
        QueryableCollection<Integer> output = new QueryableCollection<>(size);
        for (int i = 0; i < size; i++) {
            output.add(random.nextInt(max));
        }
        output.set(size / 2, max);
        return output;
    }

    private static QueryableCollection<Float> floats() {
        float max = random.nextFloat() * 1000f;
        int size = 10;
        QueryableCollection<Float> output = new QueryableCollection<>(size);
        for (int i = 0; i < size; i++) {
            output.add(random.nextFloat() * max);
        }
        output.set(size / 2, max);
        return output;
    }

    private static QueryableCollection<MyCustomString> strings() {
        return new QueryableCollection<>(Arrays.asList(new MyCustomString("1"), new MyCustomString("12345678"), new MyCustomString("123")));
    }

    private static QueryableCollection<ComplexNumber> complexNumbers() {
        return new QueryableCollection<>(Arrays.asList(new ComplexNumber(1, 1), new ComplexNumber(1, 2), new ComplexNumber(10, 1)));
    }

    private static QueryableCollection<Fraction> fractions() {
        return new QueryableCollection<>(Arrays.asList(new Fraction(1, 2), new Fraction(5, 8), new Fraction(100, 1), new Fraction(0, 1000)));
    }

    private static String print(QueryableCollection collection) {
        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(o.toString());
            builder.append(", ");
        }
        return builder.toString();
    }


}
