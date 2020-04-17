package main;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        File f = Paths.get("test_cases.txt").toFile();
        if (f.exists()) {
            System.out.print("Found test cases file, do you want to run tests? (y/n): ");
            if (scanner.nextLine().charAt(0) == 'y') {
                // run test cases
            }
        }

        System.out.println("Enter your expression and program will return the result. Enter 'q' to exit.");
        while (true) {
            String exp = scanner.next();
            if (exp.charAt(0) == 'q')
                break;
            if(checkSyntax(exp)) {
                // evaluate expression
            }
            else {
                System.out.println("Syntax error");
            }
        }

        scanner.close();
    }

    public static boolean checkSyntax(String exp) {
        String regex = "(\\s?\\d\\s?[-+*/]\\s?\\d\\s?)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(exp);
        return m.matches();
    }

    /** Convert prefix expression */
    public static String toPrefix(String exp) {
        return "";
    }

}