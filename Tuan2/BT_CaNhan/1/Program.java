import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Program {
    public static void main(String[] args) {

        Path path = Paths.get("input.txt").toAbsolutePath();
        int[][] company = null;

        try {
            company = create(path.toFile());

        } catch (IOException e) {
            System.err.println(String.format("File not found: %s", path.toString()));
            e.printStackTrace();
        } catch (NoSuchElementException e1) {
            System.err.println(String.format("Wrong file format: %s", path.toString()));
            e1.printStackTrace();
        }

        if (company == null)
            return;

        String[] menu = new String[] { "Login", "Logoff", "Search", "Exit" };
        Scanner scanner = new Scanner(System.in);
        int[] user = null;
        // print UI
        printCompany(company);
        while (true) {
            printMenu(menu);

            // waiting for user to select action
            int opt = choose(menu, scanner);
            switch (opt) {
                case 1:
                    user = readUser(scanner);
                    if (user.length > 0)
                        login(user, company);
                    break;
                case 2:
                    user = readUser(scanner);
                    if (user.length > 0)
                        logout(user, company);
                    break;
                case 3:
                    search(company, scanner);
                    break;
                default:
                    // exit program
                    scanner.close();
                    return;
            }
        }
    }

    private static void login(int[] user, int[][] company) {
        // check id
        if (user[0] < 10000 || user[0] > 99999) {
            System.out.println("Invalid id!");
            return;
        }
        // check lab
        if (user[2] >= company.length) {
            System.out.println("Invalid lab!");
            return;
        }
        // check station
        if (user[1] >= company[user[2]].length) {
            System.out.println("Invalid station!");
            return;
        }

        // check if station is already occupied by a user
        int currentUser = company[user[2]][user[1]];
        if (currentUser == 0) {
            company[user[2]][user[1]] = user[0];
            System.out.println("Successfully login user!");
        } else {
            String fm = "Station %d of lab %d is not empty";
            System.out.println(String.format(fm, user[1] + 1, user[2] + 1));
        }
        printCompany(company);
    }

    private static void logout(int[] user, int[][] company) {
        // check id
        if (user[0] < 10000 || user[0] > 99999) {
            System.out.println("Invalid id!");
            return;
        }
        // check lab
        if (user[2] >= company.length) {
            System.out.println("Invalid lab!");
            return;
        }
        // check station
        if (user[1] >= company[user[2]].length) {
            System.out.println("Invalid station!");
            return;
        }

        int id = company[user[2]][user[1]];
        if (user[0] == id) {
            company[user[2]][user[1]] = 0;
            System.out.println("Successfully log-out user!");
        } else {
            String fm = "No such user is currently log-in station %d of lab %d";
            System.out.println(String.format(fm, user[1] + 1, user[2] + 1));
        }
        printCompany(company);
    }

    private static void search(int[][] company, Scanner scanner) {
        System.out.print("Enter user's id: ");

        int id = scanner.nextInt();
        if (id < 10000 || id > 99999) {
            System.out.println("Invalid id!");
            return;
        }

        int[] rslt = searchUser(id, company);
        if (rslt.length > 0)
            System.out.println(String.format("result: station %d of lab %d", rslt[0] + 1, rslt[1] + 1));
        else
            System.out.println("No such user exists");
    }

    /**
     * find and return user's current position
     *
     * @apiNote index of lab and station start at 0
     * @return int[] where int[0] = station, int[1] = lab or empty if not found
     */
    private static int[] searchUser(int id, int[][] company) {
        for (int lab = 0; lab < company.length; ++lab) {
            for (int station = 0; station < company[lab].length; ++station) {
                if (company[lab][station] == id)
                    return new int[] { station, lab };
            }
        }
        return new int[0];
    }

    /**
     * read an user from stdin
     *
     * @apiNote index of lab and station start at 0
     * @return an array containing id, station, lab or empty if there is error
     */
    private static int[] readUser(Scanner scanner) {
        System.out.println("Enter user'id, user's station, user's lab, respectively. (example: 33011 2 5)");
        int[] user = new int[3];

        try {
            // index start at 0
            user[0] = scanner.nextInt();
            user[1] = scanner.nextInt() - 1;
            user[2] = scanner.nextInt() - 1;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            user = new int[3];
        }

        return user;
    }

    private static int[][] create(File file) throws IOException, NoSuchElementException {
        int[][] output = null;
        FileInputStream stream = new FileInputStream(file);
        Scanner scanner = new Scanner(stream);

        // read number of labs
        int M = scanner.nextInt();
        output = new int[M][0];

        // read number of stations for each lab
        for (int i = 0; i < M; ++i) {
            int N = scanner.nextInt();
            output[i] = new int[N];
        }

        // read log-on users
        int userCount = scanner.nextInt();
        for (int i = 0; i < userCount; ++i) {
            int id = scanner.nextInt();
            int station = scanner.nextInt();
            int lab = scanner.nextInt();
            output[lab - 1][station - 1] = id;
        }

        scanner.close();
        return output;
    }

    private static void printMenu(String[] menu) {
        String fm = "%d. %s";
        int i = 0;
        for (String opt : menu) {
            System.out.println(String.format(fm, ++i, opt));
        }
    }

    /**
     * prompt user to enter his action, request again if user enter an invalid
     * option
     */
    private static int choose(String[] menu, Scanner scanner) {
        int opt = -1;
        do {
            System.out.print("Your option: ");

            try {
                opt = scanner.nextInt();

            } catch (InputMismatchException e) {
                opt = -1;
                scanner.nextLine();

            }

            opt = opt >= menu.length ? menu.length : opt;
        } while (opt < 0);
        return opt;
    }

    private static void printCompany(int[][] company) {
        System.out.println("Lab\tStation");
        if (company == null)
            return;
        System.out.print("\t");
        for (int i = 0; i < company.length; ++i) {
            System.out.print(i + 1);
            System.out.print("\t");
        }
        System.out.println();

        for (int i = 0; i < company.length; ++i) {
            System.out.print(i + 1);
            System.out.print("\t");
            int[] lab = company[i];
            for (int station : lab) {
                System.out.print(station);
                System.out.print("\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}