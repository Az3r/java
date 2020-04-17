package main;

import java.util.Scanner;
import exception.SyntaxError;

public final class Program {
    public static void main(String[] args) {
        // setup
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Enter your expression and program will return the result. Enter 'q' to exit.");
        while (true) {
            String exp = scanner.nextLine();
            if (exp.charAt(0) == 'q')
                break;

            try {
                System.out.println(String.format("= %f", calculator.calculate(exp)));
            } catch (SyntaxError e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}