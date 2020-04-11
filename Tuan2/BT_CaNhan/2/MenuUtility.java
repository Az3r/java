import java.util.NoSuchElementException;
import java.util.Scanner;

public class MenuUtility {
    private MenuUtility() {
    }

    public static void printMenu(String[] menu) {
        int i = 0;
        for (String item : menu) {
            System.out.println(String.format("%d. %s", ++i, item));
        }
    }

    public static int choose(String[] menu, Scanner scanner) {
        int opt = -1;
        do {
            System.out.print("Your option: ");
            try {
                opt = scanner.nextInt();
            } catch (NoSuchElementException e) {
                opt = -1;
                scanner.nextLine();
            }
        } while (opt < 0 || opt > menu.length);
        return opt;
    }
}
