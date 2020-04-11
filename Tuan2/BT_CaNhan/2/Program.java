import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        // setting up
        Scanner scanner = new Scanner(System.in);
        Theater theater = null;
        String[] mainMenu = new String[]{"Display chart", "Purchase ticket", "Ticket prices", "Theater info", "Row info", "Exit"};
        String[] loadMenu = new String[]{"Load from file", "Load from console", "Exit"};

        // initialize data
        boolean isInitialized = false;
        while (!isInitialized) {
            MenuUtility.printMenu(loadMenu);
            int opt = MenuUtility.choose(loadMenu, scanner);
            switch (opt) {
                case 1 -> {
                    theater = loadFromFile(scanner);
                    isInitialized = theater != null;
                }
                case 2 -> {
                    theater = loadFromConsole(scanner);
                    isInitialized = theater != null;
                }
                default -> {
                    scanner.close();
                    return;
                }
            }
        }

        // main section
        theater.printSeatingChart();
        while (true) {
            MenuUtility.printMenu(mainMenu);
            int opt = MenuUtility.choose(mainMenu, scanner);
            switch (opt) {
                case 1 -> theater.printSeatingChart();
                case 2 -> purchase(theater, scanner);
                case 3 -> getTicketPrice(theater, scanner);
                case 4 -> printTheaterInfo(theater);
                case 5 -> printRowInfo(theater, scanner);
                default -> {
                    scanner.close();
                    return;
                }
            }
        }
    }

    /**
     * @return null if an error happens while loading data
     */
    private static Theater loadFromFile(Scanner scanner) {
        Theater rslt = new Theater();
        System.out.print("File: ");
        String f = scanner.next();
        File file = new File(f);
        if (rslt.readFromFile(file)) return rslt;
        return null;
    }

    /**
     * @return null if an error happens while loading data
     */
    private static Theater loadFromConsole(Scanner scanner) {
        Theater rslt = new Theater();
        if (rslt.readFromConsole(scanner)) return rslt;
        return null;
    }

    private static void purchase(Theater theater, Scanner scanner) {
        System.out.print("Enter seat (r s): ");
        try {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            if (theater.purchase(r - 1, c - 1)) {
                System.out.println("Purchase successfully!");
                System.out.println(String.format("%d tickets has been sold with total price of %d", theater.getSoldSeats(), 1000 * theater.getTotalSoldSeatPrices()));
                theater.printSeatingChart();
            } else {
                System.out.println("No such seat exists!");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Invalid input!");
        }

    }

    private static void refund(Theater theater, Scanner scanner) {
        System.out.print("Enter seat (r s): ");
        try {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            if (theater.refund(r - 1, c - 1)) {
                System.out.println("Refund successfully!");
                theater.printSeatingChart();
            } else {
                System.out.println("No such seat exists");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Invalid input!");
        }
    }

    private static void getTicketPrice(Theater theater, Scanner scanner) {
        System.out.print("Enter seat (r s): ");
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        int price = theater.getTicketPrice(r - 1, c - 1);
        if (price > 0) {
            System.out.println(String.format("Result -> row %d, seat %d, price %d", r, c, 1000 * price));
        } else {
            System.out.println("No seat found!");
        }
    }

    private static void printTheaterInfo(Theater theater) {
        String banner = "=".repeat(10) + "THEATER INFO" + "=".repeat(10);
        String footer = "=".repeat(banner.length());
        String fm = "%s\r\nTotal sold tickets: %d\r\nAvailable tickets: %d\r\n%s";
        System.out.println(String.format(fm, banner, theater.getSoldSeats(), theater.getAvailableSeat(), footer));
    }

    private static void printRowInfo(Theater theater, Scanner scanner) {
        System.out.println("Enter row: ");
        int r = scanner.nextInt();

        int sold = theater.getSoldSeats(r - 1);
        if (sold < 0) {
            System.out.println("Invalid input!");
            return;
        }
        int free = theater.getAvailableSeat(r - 1);
        System.out.println(String.format("Result -> Sold seats: %d, Available seats: %d", sold, free));
    }
}
